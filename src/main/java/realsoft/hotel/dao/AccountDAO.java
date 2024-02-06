package realsoft.hotel.dao;

import realsoft.hotel.entity.Account;
import realsoft.hotel.entity.Room;

import java.util.List;

public interface AccountDAO {


    List<Account> findAllAccounts();
    List<Account> findAllAccountsWithRoom();
    List<Account> findAccountById(Long id);
    Account findAccountByUsernameWithRooms(String username);
    Account findByEmail(String email);
    Account findByName(String name);
    Account addAccount(Account account);
    Account updateAccount(Account account);
    Account deleteAccount(Long id);
    Room deleteAccountsRentedRoom(Long roomId);
    List<Room> findAllAvailableRooms();


}
