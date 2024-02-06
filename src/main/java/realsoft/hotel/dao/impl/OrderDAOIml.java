package realsoft.hotel.dao.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import realsoft.hotel.dao.OrderDAO;
import realsoft.hotel.dao.resultSetExtractors.OrderResultSetExtractor;
import realsoft.hotel.dao.rowMapper.OrderRowMapper;
import realsoft.hotel.entity.Orders;

import java.util.List;

@Repository
public class OrderDAOIml implements OrderDAO {

    @Autowired
    JdbcTemplate jdbcTemplate;

    @Override
    public List<Orders> getAllOrders() {
        String sql = """
                SELECT * FROM ORDERS;
                """;
        return jdbcTemplate.query(sql, new OrderResultSetExtractor());
    }

    @Override
    public List<Orders> getAccountsOrders(Long id) {
        String sql = """
                SELECT * FROM orders
                where account_id = ?
                """;
        return jdbcTemplate.query(sql, new OrderResultSetExtractor(), id);
    }

    @Override
    public List<Orders> getOrderById(Long id){
        String sql = "SELECT * FROM orders WHERE id = ?";
        return jdbcTemplate.query(sql,new OrderRowMapper(),id);
    }
    @Override
    public Orders addOrder(Orders order) {
        String sql = "Insert INTO orders(account_id,from_date,to_date,room_id,type) Values(?,?,?,?,?)";
        Object[] args = { order.getAccountId(),order.getFromDate(), order.getToDate(), order.getRoomId(),order.getType()};
        int update = jdbcTemplate.update(sql, args);
        System.out.println(update + " room has been ordered!");
        return null;
    }

    @Override
    public Orders updateOrder(Long id, Orders orders) {
            String sql = """
                    UPDATE orders\s
                    SET to_date = ?
                    where id = ?
                    """;
            Object[] args = {orders.getToDate(),id};
            int i = jdbcTemplate.update(sql,args);
            return null;
    }

    @Override
    public Orders deleteOrder(Long id) {
        String sql = "DELETE FROM orders WHERE id = ?";
        jdbcTemplate.update(sql,id);
        return null;
    }
}
