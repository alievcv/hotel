package realsoft.hotel.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import realsoft.hotel.dao.AccountDAO;
import realsoft.hotel.dao.OrderDAO;
import realsoft.hotel.dao.RoomDAO;
import realsoft.hotel.dto.OrderDTOForUpdating;
import realsoft.hotel.dto.OrdersDTO;
import realsoft.hotel.entity.Account;
import realsoft.hotel.entity.Orders;
import realsoft.hotel.entity.Room;
import realsoft.hotel.exception.NoResourceFoundException;
import realsoft.hotel.mapper.OrderMapper;
import realsoft.hotel.service.AccountService;
import realsoft.hotel.service.OrderService;

import java.time.LocalTime;
import java.util.List;
import java.util.Objects;


@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {
    @Autowired
    private final OrderDAO orderDAO;
    @Autowired
    private final RoomDAO roomDAO;
    @Autowired
    private final AccountDAO accountDAO;
    @Autowired
    private final AccountService accountService;

    @Override
    public ResponseEntity<List<OrdersDTO>> getAllOrders() {
        List<OrdersDTO> orders = orderDAO.getAllOrders().stream().map(OrderMapper::toDTO).toList();
        return ResponseEntity.ok(orders);
    }

    @Override
    public ResponseEntity<List<OrdersDTO>> getAccountsOrders(Long id) {
        List<OrdersDTO> ordersDTOS = orderDAO.getAccountsOrders(id).stream().map(OrderMapper::toDTO).toList();
        return ResponseEntity.ok(ordersDTOS);
    }

    @Override
    public ResponseEntity<List<OrdersDTO>> getCurrentAccountsOrders() {
        Account account = accountDAO.findByName(accountService.getCurrentUser());
        List<OrdersDTO> ordersDTOS = orderDAO.getAccountsOrders(account.getId()).stream().map(OrderMapper::toDTO).toList();
        return ResponseEntity.ok(ordersDTOS);
    }

    @Override
    public ResponseEntity<OrdersDTO> getOrderById(Long id) {
        List<Orders> orders = orderDAO.getOrderById(id);
        Orders order = orders.get(0);
        OrdersDTO ordersDTO = OrderMapper.toDTO(order);
        if (ordersDTO == null) {
            throw new NoResourceFoundException("No 'Order' with such 'id' found!");
        }
        return ResponseEntity.ok(ordersDTO);
    }

    @SneakyThrows
    @Override
    public ResponseEntity<String> addOrder(OrdersDTO ordersDTO) {
        Account account = accountDAO.findByName(accountService.getCurrentUser());
        System.out.println(account);
        Account account1 = accountDAO.findAccountByUsernameWithRooms(accountService.getCurrentUser());
        Orders order = OrderMapper.toEntity(ordersDTO);
        if (account1 == null || account1.getRooms().size() < 3) {
            List<Room> rooms = roomDAO.findAllRooms();
            for (Room room : rooms) {
                if (!room.getIsRented() && room.getType().equalsIgnoreCase(ordersDTO.type())) {
                    Room room1 = roomDAO.findRoomById(room.getId());
                    order.setAccountId(account.getId());
                    order.setRoomId(room.getId());
                    order.setFromDate(LocalTime.now());
                    room1.setIsRented(true);
                    room1.setAccountId(account.getId());
                    roomDAO.orderChanges(room1.getId(),room1.getIsRented(),order.getAccountId());
                    orderDAO.addOrder(order);
                    break;
                }
            }
        }else if (!accountDAO.findAccountByUsernameWithRooms(account.getName()).getRooms().isEmpty() &&
                accountDAO.findAccountByUsernameWithRooms(account.getName()).getRooms().size() == 3) {
            return new ResponseEntity<>("You cannot rent more than 3 room simultaneously :( ", HttpStatus.FORBIDDEN);
        }

        if (!Objects.equals(order.getRoomId(), ordersDTO.roomId())){
            return new ResponseEntity<>("Order of the " + account.getName() + " has been successfully added!", HttpStatus.OK);
        }
        return new ResponseEntity<>("Dear "+account.getName()+","+"\n"+"We haven't available rooms according to your preferences ;(",HttpStatus.SEE_OTHER);

    }

    @SneakyThrows
    @Override
    public ResponseEntity<String> updateOrder(Long id, OrderDTOForUpdating ordersDTO) {
        Orders order = orderDAO.getOrderById(id).get(0);
        if (order.getToDate().isBefore(LocalTime.now())){
            return new ResponseEntity<>("You cannot update expired Order!",HttpStatus.BAD_REQUEST);
        }
        if (order == null) {
            throw new NoResourceFoundException(" No such order found :( ");
        }
        order.setToDate(LocalTime.parse(ordersDTO.toDate()));
        orderDAO.updateOrder(id,order);
        return ResponseEntity.ok("Order has been updated successfully:\n"+order);
    }

    @Override
    public ResponseEntity<OrdersDTO> deleteOrder(Long id) {
        List<Orders> orders = orderDAO.getOrderById(id);
        Orders order = orders.get(0);
        if (order == null) throw new NoResourceFoundException("No such order found :( ");
        List<Room> rooms = roomDAO.findAllRooms();
        for (Room room : rooms) {
            if (room.getId().equals(order.getRoomId())) {
                room.setAccountId(null);
                room.setIsRented(false);
                orderDAO.deleteOrder(id);
                break;
            }
        }
        return ResponseEntity.ok(OrderMapper.toDTO(order));
    }
}
