package hello.exception.handlerExceptionResolver;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.AllArgsConstructor;
import lombok.Data;
@Data
@AllArgsConstructor
public class ErrorResult {
    private String code;
    private String message;
}