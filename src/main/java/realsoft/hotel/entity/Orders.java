package realsoft.hotel.entity;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;

import java.time.LocalTime;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Getter
@Setter
public class Orders {
    @Id
    private Long id;
    @Column("account_id")
    private Long accountId;
    @Column("from_date")
    private LocalTime fromDate;
    @Setter
    @Column("to_date")
    private LocalTime toDate;
    @Column("room_id")
    private Long roomId;
    @Column("type")
    private String type;

    public Orders(Long id, Long aLong, String fromDate, String toDate, String type) {
        this.id = id;
        this.accountId = aLong;
        this.fromDate = LocalTime.parse(fromDate);
        this.toDate = LocalTime.parse(toDate);
        this.type  = type;
    }



}
