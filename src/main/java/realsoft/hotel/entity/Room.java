package realsoft.hotel.entity;

import jakarta.validation.constraints.NotEmpty;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;

import java.util.List;


@AllArgsConstructor
@NoArgsConstructor
@Data
public class Room {
   @Id
   private Long id;
   private Long accountId;
   @Column("type")
   @NotEmpty
   private String type;
   @Column("is_rented")
   @NotEmpty
   private Boolean isRented;
   private List<Privilege> privileges;

   public Room(Long id, Long accountId, String type, Boolean isRented) {
      this.id = id;
      this.accountId = accountId;
      this.type = type;
      this.isRented = isRented;
   }
}

