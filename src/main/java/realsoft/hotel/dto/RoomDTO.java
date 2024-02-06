package realsoft.hotel.dto;

import realsoft.hotel.entity.Account;

import java.util.List;

public record RoomDTO (

        Long id,
        Long accountId,
        String type,
        Boolean isRented,
        List<PrivilegeDTO> privileges
    )

{

}
