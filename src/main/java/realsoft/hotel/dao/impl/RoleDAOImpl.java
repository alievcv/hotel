package realsoft.hotel.dao.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import realsoft.hotel.dao.RoleDAO;
import realsoft.hotel.dao.resultSetExtractors.AccountResultSetExtractor;
import realsoft.hotel.dao.resultSetExtractors.RoleResultSetExtractor;
import realsoft.hotel.dao.rowMapper.RoleRowMapper;
import realsoft.hotel.entity.Account;
import realsoft.hotel.entity.Role;

import java.util.List;
@Repository
public class RoleDAOImpl implements RoleDAO {

    @Autowired
    private JdbcTemplate jdbcTemplate;


    @Override
    public List<Role> findAllRoles() {
        String sql = "Select * from role";
        return jdbcTemplate.query(sql, new RoleResultSetExtractor());
    }

    @Override
    public List<Role> findAllRolesByUserId(Long id) {
        String sql = """
                SELECT r.* FROM arr
                inner join account a
                ON arr.account = a.id
                inner join role r
                ON arr.role = r.id
                WHERE a.id = ?
               """;
        return jdbcTemplate.query(sql, new RoleResultSetExtractor(),id);
    }

    @Override
    public List<Role> findByName(String name) {
        String sql = """
            SELECT * from role
            where role.role = ?
            """;
        return jdbcTemplate.query(sql, new RoleRowMapper(),name);
    }

    @Override
    public void insertRoleUser(String email) {
        String query = "SELECT * FROM ACCOUNT WHERE email = ?";
        List<Account> accounts= jdbcTemplate.query(query,new AccountResultSetExtractor(),email);
        assert accounts != null;
        if (accounts.isEmpty()){
            return;
        }
        Account account = accounts.get(0);
        String sql = """
                INSERT INTO arr(account,role) VALUES(?,?)
                """;
        Object[] args= {account.getId(),2};
        jdbcTemplate.update(sql,args);
    }

    @Override
    public void insertRoleAdmin(String email) {
        String query = "SELECT * FROM ACCOUNT WHERE email = ?";
        List<Account> accounts= jdbcTemplate.query(query,new AccountResultSetExtractor(),email);
        assert accounts != null;
        if (accounts.isEmpty()){
            return;
        }
        Account account = accounts.get(0);
        String sql = """
                INSERT INTO arr(account,role) VALUES(?,?)
                """;
        Object[] args= {account.getId(),1};
        jdbcTemplate.update(sql,args);
    }
}
