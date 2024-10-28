package api_carrinho_produtos.exception;

public class UniqueViolationException extends RuntimeException {
    public UniqueViolationException(String msg) {
        super(msg);
    }
}
