package realsoft.hotel.repository;

import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.relational.core.sql.In;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import realsoft.hotel.dto.RoomDTO;
import realsoft.hotel.entity.Room;

import java.util.List;

@Repository
public interface RoomRepository extends CrudRepository<Room,Long> {
    @Query("SELECT r.* FROM room r join room_privilege_relationship rpr on r.id = rpr.room Where rpr.privilege = :id")
    List<Room> findAllByPrivileges(@Param("id")Long id);
}
