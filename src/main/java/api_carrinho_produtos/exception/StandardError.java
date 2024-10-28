package api_carrinho_produtos.exception;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.time.Instant;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class StandardError {
    Instant timestamp;
    Integer status;
    String error;
    String message;
    String path;
}
