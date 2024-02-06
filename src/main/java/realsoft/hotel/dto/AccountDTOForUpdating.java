package realsoft.hotel.dto;

public record AccountDTOForUpdating(
        String name,
        String email,
        String password,
        String phoneNumber
) {
}
