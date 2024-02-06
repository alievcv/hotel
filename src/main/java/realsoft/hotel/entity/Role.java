package realsoft.hotel.entity;

import lombok.*;
import org.springframework.data.annotation.Id;

import java.util.List;
import java.util.Set;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class Role {
    @Id
    private Long id;
    private String role;

}
