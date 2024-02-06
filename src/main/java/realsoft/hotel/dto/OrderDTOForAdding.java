package realsoft.hotel.dto;

public record OrderDTOForAdding(
        Long id,
        Long accountId,
        String fromDate ,
        String toDate,
        String type

) {
}
