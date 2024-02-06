package realsoft.hotel.dao.rowMapper;

import org.springframework.jdbc.core.RowMapper;
import realsoft.hotel.entity.Room;

import java.sql.ResultSet;
import java.sql.SQLException;

public class RoomRowMapper implements RowMapper<Room> {
    @Override
    public Room mapRow(ResultSet rs, int rowNum) throws SQLException {
        Room room = new Room();
        room.setId(rs.getLong("id"));
        room.setIsRented(rs.getBoolean("is_rented"));
        room.setType(rs.getString("type"));
        room.setAccountId(rs.getLong("user_id"));

        return room;
    }
}
