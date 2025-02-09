package commerce.ShoppingCart.dto;

import commerce.ShoppingCart.entities.Category;

import java.math.BigDecimal;

public record ProductResponseDTO(Long id,String name, String description, BigDecimal price, Category category) {
}
