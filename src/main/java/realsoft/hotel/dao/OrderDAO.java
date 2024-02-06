package realsoft.hotel.dao;

import realsoft.hotel.entity.Orders;

import java.util.List;

public interface OrderDAO {

    List<Orders> getAllOrders(); //Admin
    List<Orders> getAccountsOrders(Long id);
    List<Orders> getOrderById(Long id);
    Orders addOrder(Orders order);
    Orders updateOrder(Long id, Orders orders);
    Orders deleteOrder(Long id);

}
