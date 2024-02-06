package realsoft.hotel.dao;

import org.springframework.stereotype.Repository;
import realsoft.hotel.entity.Room;
import java.util.List;

@Repository
public interface RoomDAO  {


    List<Room> findAllRooms(); //Admin
    List<Room> findRoomsByUserId(Long id);
    Room findRoomById(Long id);

    Room addRoom(String type);
    Room update(Long id, Room room);
    Room orderChanges(Long id, Boolean isRented,Long accountId);
    Room deleteRoom(Long id);

}
