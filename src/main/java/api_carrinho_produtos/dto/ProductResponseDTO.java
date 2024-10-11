package api_carrinho_produtos.dto;

import api_carrinho_produtos.entities.Category;

import java.math.BigDecimal;
import java.util.Set;


public record ProductResponseDTO(Long id, String name, String description, BigDecimal price, Set<Category> category) {
}
