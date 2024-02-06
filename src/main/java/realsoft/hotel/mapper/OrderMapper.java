package realsoft.hotel.mapper;

import lombok.Data;
import realsoft.hotel.dto.OrderDTOForAdding;
import realsoft.hotel.dto.OrdersDTO;
import realsoft.hotel.entity.Orders;

import java.time.LocalTime;

@Data
public class OrderMapper {

    public static OrdersDTO toDTO(Orders orders){
       if (orders== null)return null;
       return new OrdersDTO(
               orders.getId(),
               orders.getAccountId(),
               orders.getFromDate().toString(),
               orders.getToDate().toString(),
               orders.getRoomId(),
               orders.getType()
       );

    }

    public static Orders toEntity(OrdersDTO ordersDTO){
        if (ordersDTO == null) return null;
        return new Orders(
                ordersDTO.id(),
                ordersDTO.accountId(),
                LocalTime.parse(ordersDTO.fromDate()),
                LocalTime.parse(ordersDTO.toDate()),
                ordersDTO.roomId(),
                ordersDTO.type()
        );
    }

    public static Orders toEntityForAdding(OrderDTOForAdding order){
        if (order == null)return null;
        return new Orders(
                order.id(),
                order.accountId(),
                order.fromDate(),
                order.toDate(),
                order.type()
        );
    }

}
