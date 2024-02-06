package realsoft.hotel.mapper;


import lombok.Data;
import realsoft.hotel.dto.PrivilegeDTO;
import realsoft.hotel.entity.Privilege;

@Data
public class PrivilegeMapper {

    public static PrivilegeDTO privilegeToDTOWithoutProperties(Privilege privilege){
        if (privilege == null) return null;
        return new PrivilegeDTO(
                privilege.getId(),
                privilege.getName()
        );
    }

    public static Privilege privilegeToEntityWithoutProperties(PrivilegeDTO privilegeDTO){
        if (privilegeDTO == null) return null;
        return new Privilege(
                privilegeDTO.getId(),
                privilegeDTO.getName()
        );
    }


}
