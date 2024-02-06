package realsoft.hotel.dao.resultSetExtractors;

import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ResultSetExtractor;
import realsoft.hotel.entity.Account;
import realsoft.hotel.entity.Privilege;
import realsoft.hotel.entity.Room;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class RoomResultSetExtractor implements ResultSetExtractor<List<Room>> {
    @Override
    public List<Room> extractData(ResultSet rs) throws SQLException, DataAccessException {
        List<Room> rooms = new ArrayList<>();

        while (rs.next()){
            Room room = new Room();
            room.setId(rs.getLong("id"));
            room.setAccountId(rs.getLong("user_id"));
            room.setType(rs.getString("type"));
            room.setIsRented(rs.getBoolean("is_rented"));

            rooms.add(room);
        }

        return rooms;
    }
}
