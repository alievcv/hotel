package realsoft.hotel.mapper;

import realsoft.hotel.dto.RoleDTO;
import realsoft.hotel.entity.Role;

public class RoleMapper {

    public static RoleDTO toDto(Role role) {
        if (role == null) return null;
        return new RoleDTO(
                role.getId(),
                role.getRole()
        );
    }

    public static Role toEntity(RoleDTO roleDTO){
        if (roleDTO == null)return null;
        return new Role(
                roleDTO.id(),
                roleDTO.name()
        );
    }
}
