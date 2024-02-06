package realsoft.hotel.exceptionHandler;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import realsoft.hotel.dto.ErrorDTO;
import realsoft.hotel.exception.NoResourceFoundException;

@RestControllerAdvice
public class exceptionHandlers {

    @ExceptionHandler(NoResourceFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ErrorDTO noResourceFoundExceptionHandler(NoResourceFoundException ex) {
        return ErrorDTO.builder().errors(ex.getMessage()).build();
    }
}
