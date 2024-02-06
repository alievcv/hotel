package realsoft.hotel.controllers;


import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import realsoft.hotel.dto.AccountDTO;
import realsoft.hotel.dto.AccountDTOForUpdating;
import realsoft.hotel.dto.OrdersDTO;
import realsoft.hotel.dto.RoomDTO;
import realsoft.hotel.service.AccountService;



import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/user")

public class AccountController {

    private final AccountService accountService;

    @GetMapping("/availableRooms")
    public ResponseEntity<List<RoomDTO>> getAllAvailableRooms(){
        return accountService.getAllAvailableRooms();
    }
    @GetMapping("/currentUser")
    public String currentUserName() {
        return accountService.getCurrentUser();
    }

    @GetMapping("/usersRooms")
    public ResponseEntity<List<RoomDTO>> getUsersRooms(){
        return accountService.getCurrentUsersRooms();
    }


    @PutMapping("/updateAccount")
    public ResponseEntity<String> updateAccount(@RequestBody AccountDTOForUpdating accountDTO){
        return accountService.updateAccount(accountDTO);
    }

    @DeleteMapping("/deleteUsersRentedRoom/{id}")
    public ResponseEntity<String> deleteUsersRentedRoomByRoomId(@PathVariable Long id){
        return accountService.deleteAccountsRentedRoom(id);
    }
}
