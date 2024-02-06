package realsoft.hotel.service;

import org.springframework.http.ResponseEntity;
import realsoft.hotel.dto.OrderDTOForUpdating;
import realsoft.hotel.dto.OrdersDTO;

import java.util.List;

public interface OrderService {

    ResponseEntity<List<OrdersDTO>> getAllOrders();
    ResponseEntity<List<OrdersDTO>> getAccountsOrders(Long id);
    ResponseEntity<List<OrdersDTO>> getCurrentAccountsOrders();

    ResponseEntity<OrdersDTO> getOrderById(Long id);
    ResponseEntity<String> addOrder(OrdersDTO ordersDTO);
    ResponseEntity<String> updateOrder(Long id, OrderDTOForUpdating ordersDTO);
    ResponseEntity<OrdersDTO> deleteOrder(Long id);

}
