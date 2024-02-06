package realsoft.hotel.dto;

import java.util.List;

public record AccountDTOForAdding(
        Long id,
        String name,
        String email,
        String password,
        String phoneNumber,
        List<RoleDTO> roles
) {
}
