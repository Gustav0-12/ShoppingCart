package api_carrinho_produtos.dto;

import java.io.Serializable;

public record StockResponseDTO(
        Long id,
        Integer quantity,
        String product) implements Serializable {
}
