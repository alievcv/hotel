package realsoft.hotel.dao.rowMapper;

import org.springframework.jdbc.core.RowMapper;
import realsoft.hotel.entity.Account;

import java.sql.ResultSet;
import java.sql.SQLException;


public class AccountRowMapper implements RowMapper<Account> {

    @Override
    public Account mapRow(ResultSet rs, int rowNum) throws SQLException {
        Account account = new Account();
        account.setId(rs.getLong("id"));
        account.setName(rs.getString("name"));
        account.setEmail(rs.getString("email"));
        account.setPassword(rs.getString("password"));
        account.setPhoneNumber(rs.getString("phone_Number"));

        return account;
    }
}
