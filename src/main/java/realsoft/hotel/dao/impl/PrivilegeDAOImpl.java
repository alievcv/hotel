package realsoft.hotel.dao.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import realsoft.hotel.dao.PrivilegeDAO;
import realsoft.hotel.dao.resultSetExtractors.PrivilegeResultSetExtractor;
import realsoft.hotel.entity.Privilege;

import java.util.List;

@Repository
public class PrivilegeDAOImpl implements PrivilegeDAO {


    @Autowired
    private JdbcTemplate jdbcTemplate;


    @Override
    public List<Privilege> findAllPrivileges() {
        return null;
    }

    @Override
    public List<Privilege> findPrivilegesByRoomId(Long id) {
        String sql = """
                SELECT p.* from rpr
                INNER JOIN room r
                ON rpr.room = r.id\s
                INNER JOIN privilege p
                ON rpr.privilege = p.id
                where r.id = ?""";

        return jdbcTemplate.query(sql,new PrivilegeResultSetExtractor(),id);
    }

    @Override
    public List<Privilege> insertPrivilegeForLuxRoom(Long id) {
        String sql = """
                Insert Into rpr(room, privilege) VALUES (?,1);
              """;
        jdbcTemplate.update(sql,id);
        String sql2 = """
                Insert Into rpr(room, privilege) VALUES (?,2);""";
        jdbcTemplate.update(sql2,id);
        return null;
    }

    @Override
    public List<Privilege> insertPrivilegeForHalfLuxRoom(Long id) {
        String sql = """
                Insert Into rpr(room, privilege) VALUES (?,1);
               """;
        jdbcTemplate.update(sql,id);

        return null;
    }

    @Override
    public List<Privilege> insertPrivilegeForStandardRoom(Long id) {
        String sql = """
                Insert Into rpr(room, privilege) VALUES (?,3);
               """;
        jdbcTemplate.update(sql,id);

        return null;
    }

}
