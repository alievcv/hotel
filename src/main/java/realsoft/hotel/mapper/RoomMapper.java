package realsoft.hotel.mapper;

import realsoft.hotel.dto.PrivilegeDTO;
import realsoft.hotel.dto.RoomDTO;
import realsoft.hotel.dto.RoomDTOForAdding;
import realsoft.hotel.entity.Room;

import java.util.ArrayList;
import java.util.stream.Collectors;

public class RoomMapper {

    public static RoomDTO toDto(Room room) {
        if (room == null)return null;
        return new RoomDTO(
                room.getId(),
                room.getAccountId(),
                room.getType(),
                room.getIsRented(),
                room.getPrivileges().stream().map(PrivilegeMapper::privilegeToDTOWithoutProperties).toList()

        );
    }


    public static Room toEntity(RoomDTO roomDTO) {
        if (roomDTO == null)return null;
        return new Room(
                roomDTO.id(),
                roomDTO.accountId(),
                roomDTO.type(),
                roomDTO.isRented()


        );
    }


    }

