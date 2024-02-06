package realsoft.hotel.dao.resultSetExtractors;

import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ResultSetExtractor;
import realsoft.hotel.entity.Privilege;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class PrivilegeResultSetExtractor implements ResultSetExtractor<List<Privilege>> {
    @Override
    public List<Privilege> extractData(ResultSet rs) throws SQLException, DataAccessException {
        List<Privilege> privileges = new ArrayList<>();

        while (rs.next()){

            Privilege privilege = new Privilege();

            privilege.setId(rs.getLong("id"));
            privilege.setName(rs.getString("name"));

            privileges.add(privilege);
        }
        return privileges;
    }
}
