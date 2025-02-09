package commerce.ShoppingCart.dto;

import commerce.ShoppingCart.entities.Product;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.List;

public record ShoppingCartDTO(
        Long id,
        Instant creationTime,
        BigDecimal totalPrice,
        UserResponseDTO user,
        List<CartProductsDTO> items) {

    public record CartProductsDTO(
            Long id,
            Integer quantity,
            BigDecimal price,
            Product product) {
    }
}

