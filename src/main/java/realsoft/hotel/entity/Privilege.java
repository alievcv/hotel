package realsoft.hotel.entity;


import lombok.*;
import org.springframework.data.annotation.Id;


@AllArgsConstructor
@NoArgsConstructor
@Data
public class  Privilege {
    @Id
    private Long id;
    private String name;



}