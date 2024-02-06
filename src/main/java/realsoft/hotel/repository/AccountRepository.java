package realsoft.hotel.repository;

import org.springframework.context.annotation.Bean;
import org.springframework.data.repository.CrudRepository;

import org.springframework.stereotype.Repository;
import realsoft.hotel.entity.Account;

@Repository("accountRepository")
public interface AccountRepository extends CrudRepository<Account, Long> {



}
