package realsoft.hotel.service;

import org.springframework.http.ResponseEntity;
import realsoft.hotel.dto.AccountDTO;
import realsoft.hotel.dto.AccountDTOForAdding;
import realsoft.hotel.dto.AccountDTOForUpdating;
import realsoft.hotel.dto.RoomDTO;

import java.util.List;
public interface AccountService {

    ResponseEntity<List<AccountDTOForAdding>> getAllAccounts();
    ResponseEntity<List<AccountDTO>> getAllAccountsWithRoom();
    ResponseEntity<AccountDTO> getAccountById(Long id);

    ResponseEntity<List<RoomDTO>> getCurrentUsersRooms();

    ResponseEntity<String> addAccount(AccountDTOForAdding accountDTOForAdding);


    ResponseEntity<String> updateAccount(AccountDTOForUpdating accountDTO);

    ResponseEntity<String> deleteAccount(Long id);

    ResponseEntity<List<RoomDTO>> getAllAvailableRooms();

    ResponseEntity<String> deleteAccountsRentedRoom(Long id);

    String getCurrentUser();

    ResponseEntity<String> addRoleAdmin(String username);


}
