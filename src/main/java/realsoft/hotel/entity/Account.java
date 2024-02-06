package realsoft.hotel.entity;


import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.NotEmpty;
import lombok.*;

import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;

import java.util.List;


@AllArgsConstructor
@NoArgsConstructor
@Data
public class Account {

    @Id
    private Long id;
    @NotEmpty
    @Column("name")
    private String name;
    @NotEmpty
    @Column("email")
    private String email;
    @NotEmpty
    @Column("password")
    private String password;
    @NotEmpty
    @Column("phone_number")
    @Digits(fraction = 0,integer = 9)
    private String phoneNumber;
    private List<Room> rooms;
    private List<Role> roles;

    public Account(Long id, String name, String email, String password, String phoneNumber) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.password = password;
        this.phoneNumber = phoneNumber;
    }


}
