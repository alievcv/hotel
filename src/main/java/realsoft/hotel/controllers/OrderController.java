package realsoft.hotel.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import realsoft.hotel.dto.OrderDTOForUpdating;
import realsoft.hotel.dto.OrdersDTO;
import realsoft.hotel.service.OrderService;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/order")
public class OrderController {


    private final OrderService orderService;


    @GetMapping("/myOrders")
    public ResponseEntity<List<OrdersDTO>> getCurrentUsersOrders(){
        return orderService.getCurrentAccountsOrders();
    }
    @PostMapping("/addOrder")
    public ResponseEntity<String> addOrder(@RequestBody OrdersDTO ordersDTO) {
        return orderService.addOrder(ordersDTO);
    }

    @PutMapping("/updateOrder")
    public ResponseEntity<String> updateOrder(Long id, OrderDTOForUpdating ordersDTO) {
        return orderService.updateOrder(id, ordersDTO);

    }

    @DeleteMapping("/deleteOrder/{id}")
    public ResponseEntity<OrdersDTO> deleteOrder(@PathVariable Long id) {
        return orderService.deleteOrder(id);
    }


}



