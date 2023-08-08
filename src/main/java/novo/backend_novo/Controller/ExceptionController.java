package novo.backend_novo.Controller;

import lombok.extern.slf4j.Slf4j;
import novo.backend_novo.Response.BasicResponse;
import novo.backend_novo.Response.ErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.io.IOException;

@ControllerAdvice
@Slf4j
public class ExceptionController {

    @ExceptionHandler({IllegalArgumentException.class})
    public ResponseEntity<? extends BasicResponse> IllegalArgumentHandler(IllegalArgumentException e){
        log.warn("-----IllegalArgumentException-----");
        e.printStackTrace();
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(new ErrorResponse(e.getMessage()));
    }

    @ExceptionHandler({RuntimeException.class})
    public ResponseEntity<? extends BasicResponse> RunTimeHandler(RuntimeException e){
        log.warn("-----RunTimeException-----");
        e.printStackTrace();
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(new ErrorResponse(e.getMessage()));
    }

    @ExceptionHandler({MethodArgumentNotValidException.class})
    public ResponseEntity<? extends BasicResponse> ValidHandler(MethodArgumentNotValidException e){
        log.warn("-----ValidException-----");
        e.printStackTrace();
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(new ErrorResponse(e.getBindingResult().getAllErrors().get(0).getDefaultMessage()));
    }

    @ExceptionHandler(IOException.class)
    public ResponseEntity<? extends BasicResponse> IOHandler(IOException e){
        log.warn("-----IOException-----");
        e.printStackTrace();
        return ResponseEntity.status(HttpStatus.NO_CONTENT)
                .body(new ErrorResponse(e.getMessage()));
    }
}
