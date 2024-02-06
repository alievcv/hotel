package realsoft.hotel.dto;


public record OrdersDTO(
        Long id,
        Long accountId,
        String fromDate ,
        String toDate,
        Long roomId,
        String type

) {

}
