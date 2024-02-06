package realsoft.hotel.mapper;

import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import realsoft.hotel.dto.AccountDTO;
import realsoft.hotel.dto.AccountDTOForAdding;
import realsoft.hotel.entity.Account;
import realsoft.hotel.entity.Room;

import java.util.ArrayList;
import java.util.stream.Collectors;

@Data
public class AccountMapper {

    private PasswordEncoder passwordEncoder;
    public static AccountDTO toDTO(Account account){
        if (account == null) return null;
        return new AccountDTO(
                account.getId(),
                account.getName(),
                account.getEmail(),
                account.getPassword(),
                account.getPhoneNumber(),
                account.getRooms().stream().map(RoomMapper::toDto).toList(),
                account.getRoles().stream().map(RoleMapper::toDto).toList()


        );
    }

    public static Account toEntity(AccountDTO accountDTO){
        if (accountDTO == null) return null;
        return new Account(
                accountDTO.id(),
                accountDTO.name(),
                accountDTO.email(),
                accountDTO.password(),
                accountDTO.phoneNumber(),
                accountDTO.rooms().stream().map(RoomMapper::toEntity).toList(),
                accountDTO.roles().stream().map(RoleMapper::toEntity).toList()

        );
    }

    public static Account toEntityForAdding(AccountDTOForAdding accountDTOForAdding){
        if (accountDTOForAdding==null) return null;
        return new Account(
                accountDTOForAdding.id(),
                accountDTOForAdding.name(),
                accountDTOForAdding.email(),
                accountDTOForAdding.password(),
                accountDTOForAdding.phoneNumber()
        );
    }

    public static AccountDTOForAdding toDTOWithoutRooms(Account account){
        if (account == null)return null;
        return new AccountDTOForAdding(
                account.getId(),
                account.getName(),
                account.getEmail(),
                account.getPassword(),
                account.getPhoneNumber(),
                account.getRoles().stream().map(RoleMapper::toDto).toList()

        );
    }
}
