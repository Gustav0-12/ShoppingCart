package api_carrinho_produtos.exception;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.Instant;

@RestControllerAdvice
public class ControllerExceptionHandler {

    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity entityNotFound(RuntimeException exception, HttpServletRequest request) {
        String error = "Not found";
        String message = "Entity not found";
        HttpStatus status = HttpStatus.NOT_FOUND;
        StandardError standardError = new StandardError(Instant.now(), status.value(), error, message, request.getRequestURI());
        return ResponseEntity.status(status).body(standardError);
    }

    @ExceptionHandler(OutOfStockException.class)
    public ResponseEntity outOfStock(RuntimeException exception, HttpServletRequest request) {
        String error = "Conflict";
        String message = "Product is out of stock";
        HttpStatus status = HttpStatus.CONFLICT;
        StandardError standardError = new StandardError(Instant.now(), status.value(), error, message, request.getRequestURI());
        return ResponseEntity.status(status).body(standardError);
    }

    @ExceptionHandler(UniqueViolationException.class)
    public ResponseEntity uniqueViolation(RuntimeException exception, HttpServletRequest request) {
        String error = "Conflict";
        String message = "Entity must be unique";
        HttpStatus status = HttpStatus.CONFLICT;
        StandardError standardError = new StandardError(Instant.now(), status.value(), error, message, request.getRequestURI());
        return ResponseEntity.status(status).body(standardError);
    }

    @ExceptionHandler(WrongCredentialsException.class)
    public ResponseEntity wrongCredentials(RuntimeException exception, HttpServletRequest request) {
        String error = "Bad request";
        String message = "Wrong credentials";
        HttpStatus status = HttpStatus.BAD_REQUEST;
        StandardError standardError = new StandardError(Instant.now(), status.value(), error, message, request.getRequestURI());
        return ResponseEntity.status(status).body(standardError);
    }
}
