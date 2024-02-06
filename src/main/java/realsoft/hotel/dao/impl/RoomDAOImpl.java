package realsoft.hotel.dao.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import realsoft.hotel.dao.PrivilegeDAO;
import realsoft.hotel.dao.RoomDAO;
import realsoft.hotel.dao.resultSetExtractors.RoomResultSetExtractor;
import realsoft.hotel.entity.Room;


import java.util.List;
import java.util.Objects;

@Repository
public class RoomDAOImpl implements RoomDAO {

    @Autowired
    private JdbcTemplate jdbcTemplate;
    @Autowired
    private PrivilegeDAO privilegeDAO;


    @Override
    public List<Room> findAllRooms(){
        String query = """
                SELECT DISTINCT r.* from rpr
                INNER JOIN room r
                ON rpr.room = r.id\s
                INNER JOIN privilege p
                ON rpr.privilege = p.id\s""";
        List<Room> rooms = jdbcTemplate.query(query, new RoomResultSetExtractor());
        assert rooms != null;
        for (Room room : rooms) {
            room.setPrivileges(privilegeDAO.findPrivilegesByRoomId(room.getId()));
        }
        return rooms;
    }

    @Override
    public List<Room> findRoomsByUserId(Long id) {
        String sql = """
                SELECT DISTINCT r.* from rpr
                INNER JOIN room r
                ON rpr.room = r.id
                INNER JOIN privilege p
                ON rpr.privilege = p.id
                WHERE user_id = ?
                        """;
        List<Room> rooms = jdbcTemplate.query(sql, new RoomResultSetExtractor(),id);
        assert rooms != null;
        for (Room room : rooms) {
            room.setPrivileges(privilegeDAO.findPrivilegesByRoomId(room.getId()));
        }
        return rooms;
    }

    @Override
    public Room findRoomById(Long id) {
        String sql = "SELECT DISTINCT r.* from rpr\n" +
                "                INNER JOIN room r\n" +
                "                ON rpr.room = r.id\n" +
                "                INNER JOIN privilege p\n" +
                "                ON rpr.privilege = p.id" +
                "                WHERE r.id = ?";
        List<Room> rooms = Objects.requireNonNull(jdbcTemplate.query(sql, new RoomResultSetExtractor(), id));
        if (!rooms.isEmpty()){
            Room room = rooms.get(0);
            room.setPrivileges(privilegeDAO.findPrivilegesByRoomId(room.getId()));
            return room;
        }
        return null;
    }


    @Override
    public Room addRoom(String type) {
        String query = "INSERT INTO room(is_rented,type) values (?,?)";
        Object[] args = {false,type};
        int update = jdbcTemplate.update(query, args);
        System.out.println(update+" rows added!");
        String sql = """
                SELECT * FROM room
                ORDER BY id DESC
                LIMIT 1""";
        Room room = Objects.requireNonNull(jdbcTemplate.query(sql, new RoomResultSetExtractor())).get(0);
        if (room.getType().equalsIgnoreCase("lux")){
            privilegeDAO.insertPrivilegeForLuxRoom(room.getId());
        }else if (room.getType().equalsIgnoreCase("half-lux") || room.getType().equalsIgnoreCase("halflux")){
            privilegeDAO.insertPrivilegeForHalfLuxRoom(room.getId());
        }else if (room.getType().equalsIgnoreCase("standard")){
            privilegeDAO.insertPrivilegeForStandardRoom(room.getId());
        }
        return room;

    }

    @Override
    public Room update(Long id, Room room) {
        String sql = """
                UPDATE room
                SET
                    type = ?
                WHERE id = ?
                """;

        Object[] args = {room.getType(),id};
        jdbcTemplate.update(sql,args);
        return null;
    }

    @Override
    public Room orderChanges(Long id,  Boolean isRented,Long accountId) {
        String sql = """
                UPDATE room
                SET user_id =?,
                    is_rented = ?
                WHERE id = ?
                """;

        Object[] args = {accountId,isRented,id};
        jdbcTemplate.update(sql,args);
        return null;
    }

    @Override
    public Room deleteRoom(Long id) {
        String query = "DELETE FROM room WHere Id = ?";
        int rows = jdbcTemplate.update(query,id);
        System.out.println("Deleted " + rows+" rows");
        return null;
    }


}
