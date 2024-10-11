package api_carrinho_produtos.dto;
import java.math.BigDecimal;
import java.util.List;


public record ProductRequestDTO(String name, String description, BigDecimal price, Integer quantity, List<Long> categoryId) {
}
