package realsoft.hotel.controllers;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import realsoft.hotel.dto.*;
import realsoft.hotel.entity.Orders;
import realsoft.hotel.entity.Privilege;
import realsoft.hotel.service.AccountService;
import realsoft.hotel.service.OrderService;
import realsoft.hotel.service.RoomService;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    RoomService roomService;
    @Autowired
    AccountService accountService;
    @Autowired
    OrderService orderService;

    //TODO Room contollers
    @GetMapping("/allRooms")
    public ResponseEntity<List<RoomDTO>> getAllRooms() {
        return roomService.getAllRooms();
    }

    @GetMapping("/roomById/{id}")
    public ResponseEntity<RoomDTO> getRoomById(@PathVariable Long id){
        return roomService.getRoomById(id);
    }

    @PostMapping("/createRoom")
    public ResponseEntity<String> createRoom(@RequestBody RoomDTOForAdding roomDTOForAdding){
        return roomService.createRoom(roomDTOForAdding);
    }

    @DeleteMapping("/deleteRoom/{id}")
    public ResponseEntity<String> deleteRoom(@PathVariable Long id){
        return roomService.deleteRoom(id);
    }

    //TODO Account Controllers

    @GetMapping("/allAccounts")
    public ResponseEntity<List<AccountDTOForAdding>> getAllAccounts(){
        return accountService.getAllAccounts();
    }

    @GetMapping("/allAccountsWithRoom")
    public ResponseEntity<List<AccountDTO>> getAllAccountsWithRoom(){
        return accountService.getAllAccountsWithRoom();
    }
    @GetMapping("/accountById/{id}")
    public ResponseEntity<AccountDTO> getAccountById(@PathVariable Long id){
        return accountService.getAccountById(id);
    }

    @DeleteMapping("/deleteUser/{id}")
    public ResponseEntity<String> deleteAccount(@PathVariable Long id){
        return accountService.deleteAccount(id);
    }

    @PutMapping("/addRoleAdmin/{username}")
    public ResponseEntity<String> addRoleAdminToUser(@PathVariable String username){
        return accountService.addRoleAdmin(username);
    }

    //TODO Order controllers
    @GetMapping("/allOrders")
    public ResponseEntity<List<OrdersDTO>> getAllOrders() {
        return orderService.getAllOrders();
    }

    @GetMapping("/ordersByUserId/{id}")
    public ResponseEntity<List<OrdersDTO>> getAccountsOrders(@PathVariable Long id) {
        return orderService.getAccountsOrders(id);
    }

}
