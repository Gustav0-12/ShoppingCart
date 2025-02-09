package commerce.ShoppingCart.exceptions;

public class UniqueViolationException extends RuntimeException {
    public UniqueViolationException(String msg) {
        super(msg);
    }
}
