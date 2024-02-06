package realsoft.hotel.dao.resultSetExtractors;

import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ResultSetExtractor;
import realsoft.hotel.entity.Orders;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

public class OrderResultSetExtractor implements ResultSetExtractor<List<Orders>>{
    @Override
    public List<Orders> extractData(ResultSet rs) throws SQLException, DataAccessException {
        List<Orders> orders = new ArrayList<>();

        while (rs.next()){
            Orders order = new Orders();
            order.setId(rs.getLong("id"));
            order.setAccountId(rs.getLong("account_id"));
            order.setFromDate(rs.getObject("from_date",LocalTime.class));
            order.setToDate(rs.getObject("to_date",LocalTime.class));
            order.setRoomId(rs.getLong("room_id"));
            order.setType(rs.getString("type"));
            orders.add(order);
        }
        return orders;
    }
}
