package realsoft.hotel.dto;

import lombok.Getter;
import lombok.Setter;
import realsoft.hotel.entity.Orders;
import realsoft.hotel.entity.Role;
import realsoft.hotel.entity.Room;

import java.util.ArrayList;
import java.util.List;



public record AccountDTO(
    Long id,
    String name,
    String email,
    String password,
    String phoneNumber,
    List<RoomDTO> rooms,
    List<RoleDTO> roles

){}
