package realsoft.hotel.dto;

import realsoft.hotel.entity.Account;
import realsoft.hotel.entity.Privilege;
import realsoft.hotel.entity.Type;

import java.util.List;

public class RoomDto {

    private Long roomId;
    private Account user;
    private Type roomType;
    private Boolean isRented;
    private Long rentalPeriod;
    private List<Privilege> privileges;


}
