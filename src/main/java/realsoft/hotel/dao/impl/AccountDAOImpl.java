package realsoft.hotel.dao.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Repository;
import realsoft.hotel.dao.AccountDAO;
import realsoft.hotel.dao.PrivilegeDAO;
import realsoft.hotel.dao.RoleDAO;
import realsoft.hotel.dao.RoomDAO;
import realsoft.hotel.dao.resultSetExtractors.AccountResultSetExtractor;
import realsoft.hotel.dao.resultSetExtractors.RoomResultSetExtractor;
import realsoft.hotel.dao.rowMapper.AccountRowMapper;
import realsoft.hotel.entity.Account;
import realsoft.hotel.entity.Role;
import realsoft.hotel.entity.Room;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Repository
@RequiredArgsConstructor
public class AccountDAOImpl implements AccountDAO {

    private JdbcTemplate jdbcTemplate;
    private RoomDAO roomDAO;
    private PrivilegeDAO privilegeDAO;
    private RoleDAO roleDAO;

    @Autowired
    public AccountDAOImpl(JdbcTemplate jdbcTemplate, RoomDAO roomDAO, PrivilegeDAO privilegeDAO, RoleDAO roleDAO) {
        this.jdbcTemplate = jdbcTemplate;
        this.roomDAO = roomDAO;
        this.privilegeDAO = privilegeDAO;
        this.roleDAO = roleDAO;
    }

    @Override
    public List<Account> findAllAccounts() {
        String sql = """
                SELECT a.*,r.* FROM arr
                inner join account a
                ON arr.account = a.id
                inner join role r
                ON arr.role = r.id
               """;
        List<Account> accounts = jdbcTemplate.query(sql, new AccountResultSetExtractor());
        assert accounts != null;
        for (Account account : accounts) {
            account.setRoles(roleDAO.findAllRolesByUserId(account.getId()));
        }
        return accounts;
    }

    @Override
    public List<Account> findAllAccountsWithRoom() {
        String query = """
                SELECT DISTINCT a.*, r.* FROM account a
                inner join room r
                on a.id = r.user_id""";
        List<Account> accounts = jdbcTemplate.query(query, new AccountResultSetExtractor());
        assert accounts != null;
        for (Account account : accounts) {
            account.setRooms(roomDAO.findRoomsByUserId(account.getId()));
        }
        for (Account account : accounts) {
            account.setRoles(roleDAO.findAllRolesByUserId(account.getId()));
        }
        return accounts;
    }

    @Override
    public List<Account> findAccountById(Long id) {
        String query = " SELECT * FROM account where id = ?";
        List<Account> accounts = jdbcTemplate.query(query, new AccountResultSetExtractor(), id);
        assert accounts != null;
        for (Account account : accounts) {
            account.setRooms(roomDAO.findRoomsByUserId(account.getId()));
        }
        for (Account account : accounts) {
            account.setRoles(roleDAO.findAllRolesByUserId(account.getId()));
        }
        return accounts;
    }

    @Override
    public Account findAccountByUsernameWithRooms(String username) {
        String query = """
                SELECT a.*, r.* FROM account a
                inner join room r
                on a.id = r.user_id
                Where a.name = ?""";
        List<Account> accounts = Objects.requireNonNull(jdbcTemplate.query(query, new AccountResultSetExtractor(), username));
        assert accounts != null;
        if (!accounts.isEmpty()) {
            Account a = accounts.get(0);
            a.setRooms(roomDAO.findRoomsByUserId(a.getId()));
            a.setRoles(roleDAO.findAllRolesByUserId(a.getId()));
            return a;
        }
        return null;
    }

    /*

            String query = "INSERT INTO room(user_id, type, is_rented) values (?,?,?)";
            Object[] args = {room.getAccountId(),room.getType(),room.getIsRented()};
            int update = jdbcTemplate.update(query, args);
            System.out.println(update+" rows updated!");
            return room;


     */
    @Override
    public Account addAccount(Account account) {
        String query = "INSERT INTO account(name, email, password,phone_number) VALUES(?,?,?,?)";
        Object[] args = {account.getName(), account.getEmail(), account.getPassword(), account.getPhoneNumber()};
        int update = jdbcTemplate.update(query, args);
        System.out.println(update + " account added!");
        return account;
    }


    @Override
    public Account updateAccount(Account account) {
        String sql = """
                UPDATE account
                Set name = ?,
                \temail = ?,
                \tpassword = ?,
                \tphone_Number = ?
                WHERE id = ?""";

        Object[] args = {account.getName(), account.getEmail(), account.getPassword(), account.getPhoneNumber(), account.getId()};
        int update = jdbcTemplate.update(sql, args);
        System.out.println(update + " account updated");
        return null;
    }

    @Override
    public Account deleteAccount(Long id) {
        String sql = """
                    DELETE FROM room Where user_id = ?
                """;
        jdbcTemplate.update(sql, id);
        String sql1 = """
                    DELETE FROM orders Where account_id = ?
                """;
        jdbcTemplate.update(sql1, id);
        String sql2 = """
                    DELETE FROM account Where id = ?
                """;
        jdbcTemplate.update(sql2, id);
        return null;
    }


    @Override
    public Room deleteAccountsRentedRoom(Long roomId) {
        String sql = """
                Update room
                Set user_id = NULL,
                    is_rented = false
                WHERE id = ?
                """;
        jdbcTemplate.update(sql, roomId);

        String sql2 = "DELETE FROM orders WHERE room_id = ?";
        jdbcTemplate.update(sql2, roomId);
        return null;
    }

    @Override
    public List<Room> findAllAvailableRooms() {
        /*List<Room> rooms = roomDAO.findAllRooms();
        List<Room> roomsList = new ArrayList<>();
        for (Room room : rooms) {
            if (!room.getIsRented() && room.getAccountId() == null)
                roomsList.add(room);
        }
        return roomsList;*/
        String query = """
                SELECT DISTINCT r.* from rpr
                INNER JOIN room r
                ON rpr.room = r.id\s
                INNER JOIN privilege p
                ON rpr.privilege = p.id\s
                WHERE is_rented = false""";
        List<Room> rooms = jdbcTemplate.query(query, new RoomResultSetExtractor());
        assert rooms != null;
        for (Room room : rooms) {
            room.setPrivileges(privilegeDAO.findPrivilegesByRoomId(room.getId()));
        }
        return rooms;
    }

    @Override
    public Account findByEmail(String email) {
        String sql = "SELECT * FROM account where email = ?";
        List<Account> accounts = Objects.requireNonNull(jdbcTemplate.query(sql, new AccountResultSetExtractor(), email));
        if (!accounts.isEmpty()) {
            Account account = accounts.get(0);
            account.setRoles(roleDAO.findAllRolesByUserId(account.getId()));
            return account;
        }
        return null;
    }

    @Override
    public Account findByName(String name) {
        String sql = "SELECT * FROM account where name = ?";
        List<Account> accounts = jdbcTemplate.query(sql, new AccountResultSetExtractor(), name);
        if (accounts != null && !accounts.isEmpty()) {
            Account account = accounts.get(0);
            account.setRoles(roleDAO.findAllRolesByUserId(account.getId()));
            return account;
        }
        return null;
    }
}
