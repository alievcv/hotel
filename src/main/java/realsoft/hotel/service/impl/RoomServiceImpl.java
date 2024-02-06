package realsoft.hotel.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import realsoft.hotel.dao.OrderDAO;
import realsoft.hotel.dao.PrivilegeDAO;
import realsoft.hotel.dao.RoomDAO;
import realsoft.hotel.dto.RoomDTO;
import realsoft.hotel.dto.RoomDTOForAdding;
import realsoft.hotel.entity.Orders;
import realsoft.hotel.entity.Room;
import realsoft.hotel.exception.NoResourceFoundException;
import realsoft.hotel.mapper.RoomMapper;
import realsoft.hotel.service.RoomService;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class RoomServiceImpl implements RoomService {

    @Autowired
    private final RoomDAO roomDAO;
    @Autowired
    private final OrderDAO orderDAO;
    @Autowired
    private final PrivilegeDAO privilegeDAO;

    @Override
    public ResponseEntity<List<RoomDTO>> getAllRooms(){
        List<Room> all = roomDAO.findAllRooms();
        return ResponseEntity.ok(all.stream().map(RoomMapper::toDto).collect(Collectors.toList()));

    }

    @Override
    public ResponseEntity<RoomDTO> getRoomById(Long id) {
        RoomDTO roomDTO = RoomMapper.toDto(roomDAO.findRoomById(id));
        return ResponseEntity.ok(roomDTO);
    }

    @Override
    public ResponseEntity<String > createRoom(RoomDTOForAdding roomDTOForAdding) {
        /*Room room = roomRepository.save(RoomMapper.toEntity(roomDto));
        return ResponseEntity.ok(RoomMapper.toDto(room));*/
        if (    roomDTOForAdding.type().equalsIgnoreCase("lux") ||
                roomDTOForAdding.type().equalsIgnoreCase("halflux") ||
                roomDTOForAdding.type().equalsIgnoreCase("half-lux")||
                roomDTOForAdding.type().equalsIgnoreCase("standard")
                ){
            Room room = roomDAO.addRoom(roomDTOForAdding.type());
            return new ResponseEntity<>("New room is added "+room ,HttpStatus.OK);
        }
        return new ResponseEntity<>("The room type can be only 'LUX', 'HALF-LUX' or 'STANDARD'",HttpStatus.BAD_REQUEST);

    }


    @Override
    public ResponseEntity<String> deleteRoom(Long roomId) {
        Room room = roomDAO.findRoomById(roomId);
        List<Orders> orders = orderDAO.getAllOrders();
        if (room == null)return new ResponseEntity<>("Couldn't found room with ID:"+roomId,HttpStatus.NOT_FOUND);
        if (room.getIsRented()){
            return new ResponseEntity<>("You cannot delete room while it is rented!",HttpStatus.BAD_REQUEST);
        }else {
            room.setAccountId(null);
            room.setPrivileges(null);
            roomDAO.deleteRoom(roomId);
            for (Orders order : orders) {
                if (Objects.equals(order.getRoomId(), roomId)) {
                    order.setRoomId(null);
                    orderDAO.deleteOrder(order.getId());
                }
            }
        }

            return ResponseEntity.ok("");
    }


}
