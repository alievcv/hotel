package realsoft.hotel.dao.resultSetExtractors;

import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ResultSetExtractor;
import realsoft.hotel.entity.Account;
import realsoft.hotel.entity.Orders;
import realsoft.hotel.entity.Role;
import realsoft.hotel.entity.Room;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class AccountResultSetExtractor implements ResultSetExtractor<List<Account>> {
    @Override
    public List<Account> extractData(ResultSet rs) throws SQLException, DataAccessException {
        List<Account> accounts = new ArrayList<>();
        while (rs.next()){
            Account account = new Account();

            account.setId(rs.getLong("id"));
            account.setName(rs.getString("name"));
            account.setEmail(rs.getString("email"));
            account.setPassword(rs.getString("password"));
            account.setPhoneNumber(rs.getString("phone_Number"));
            accounts.add(account);
        }


        return accounts;
    }
}
