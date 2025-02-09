package commerce.ShoppingCart.dto;

import commerce.ShoppingCart.entities.Product;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.List;

public record ShoppingCartDTO(
        Long id,
        Instant creationTime,
        BigDecimal totalPrice,
        List<CartProductsDTO> items,
        UserResponseDTO user) {

    public record CartProductsDTO(
            Integer quantity,
            BigDecimal price,
            Product product) {
    }
}

