package api_carrinho_produtos.exception;

public class WrongCredentialsException extends RuntimeException {
    public WrongCredentialsException(String msg) {
        super(msg);
    }
}
