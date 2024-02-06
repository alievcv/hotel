package realsoft.hotel.dao;

import realsoft.hotel.entity.Role;

import java.util.List;

public interface RoleDAO {
    List<Role> findAllRoles();
    List<Role> findAllRolesByUserId(Long id);
    List<Role> findByName(String name);
    void insertRoleUser(String email);
    void insertRoleAdmin(String email);

}
