package realsoft.hotel.service;

import org.springframework.http.ResponseEntity;
import realsoft.hotel.dto.RoomDTO;
import realsoft.hotel.dto.RoomDTOForAdding;

import java.util.List;

public interface RoomService {

    ResponseEntity<List<RoomDTO>> getAllRooms();

    ResponseEntity<RoomDTO> getRoomById(Long id);


    ResponseEntity<String> createRoom(RoomDTOForAdding roomDTOForAdding);


    ResponseEntity<String> deleteRoom(Long roomId);

}
