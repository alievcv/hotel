package realsoft.hotel.dao;

import realsoft.hotel.entity.Privilege;

import java.util.List;

public interface PrivilegeDAO {
    List<Privilege> findAllPrivileges();
    List<Privilege> findPrivilegesByRoomId(Long id);
    List<Privilege> insertPrivilegeForLuxRoom(Long id);
    List<Privilege> insertPrivilegeForHalfLuxRoom(Long id);
    List<Privilege> insertPrivilegeForStandardRoom(Long id);
}
