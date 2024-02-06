package realsoft.hotel.dao.resultSetExtractors;

import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.security.core.parameters.P;
import realsoft.hotel.entity.Role;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class RoleResultSetExtractor implements ResultSetExtractor<List<Role>> {
    @Override
    public List<Role> extractData(ResultSet rs) throws SQLException, DataAccessException {
        List<Role> roles = new ArrayList<>();
        while (rs.next()){
            Role role = new Role();
            role.setId(rs.getLong("id"));
            role.setRole(rs.getString("role"));
            roles.add(role);
        }
        return roles;
    }
}
