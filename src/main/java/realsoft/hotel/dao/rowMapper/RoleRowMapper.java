package realsoft.hotel.dao.rowMapper;

import org.springframework.jdbc.core.RowMapper;
import realsoft.hotel.entity.Role;

import java.sql.ResultSet;
import java.sql.SQLException;

public class RoleRowMapper implements RowMapper<Role> {
    @Override
    public Role mapRow(ResultSet rs, int rowNum) throws SQLException {
        Role role = new Role();
        role.setId(rs.getLong("id"));
        role.setRole(rs.getString("role"));
        return role;
    }
}
