package realsoft.hotel.service;

import org.springframework.http.ResponseEntity;
import realsoft.hotel.dto.RoomDto;
import realsoft.hotel.entity.Type;

import java.util.List;
import java.util.Optional;

public interface RoomService {

    ResponseEntity<List<RoomDto>> getAllRooms();

    ResponseEntity<RoomDto> createRoom(RoomDto roomDto);

    ResponseEntity<RoomDto> updateRoom(Long roomId, RoomDto roomDto);

    ResponseEntity<RoomDto> deleteRoom(Long roomId);

    ResponseEntity<RoomDto> getRoomsByFilter(Optional<Type> type);

    ResponseEntity<RoomDto> getAvailableRooms();
}
