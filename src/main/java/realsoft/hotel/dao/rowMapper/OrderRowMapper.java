package realsoft.hotel.dao.rowMapper;

import org.springframework.jdbc.core.RowMapper;
import realsoft.hotel.entity.Orders;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalTime;
import java.util.List;

public class OrderRowMapper implements RowMapper<Orders> {
    @Override
    public Orders mapRow(ResultSet rs, int rowNum) throws SQLException {
        Orders order = new Orders();
        order.setId(rs.getLong("id"));
        order.setAccountId(rs.getLong("account_id"));
        order.setFromDate(rs.getObject("from_date",LocalTime.class));
        order.setToDate(rs.getObject("to_date",LocalTime.class));
        order.setRoomId(rs.getLong("room_id"));
        order.setType(rs.getString("type"));
    return order;
    }
}
