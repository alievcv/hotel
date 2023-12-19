package realsoft.hotel.service;

import org.springframework.http.ResponseEntity;
import realsoft.hotel.dto.AccountDto;
import java.util.List;
public interface AccountService {

    ResponseEntity<List<AccountDto>> getAllAccounts();

    ResponseEntity<AccountDto> addAccount(AccountDto accountDto);

    ResponseEntity<AccountDto> updateAccount(Long id, AccountDto accountDto);

    ResponseEntity<AccountDto> deleteAccount(Long id);






}
