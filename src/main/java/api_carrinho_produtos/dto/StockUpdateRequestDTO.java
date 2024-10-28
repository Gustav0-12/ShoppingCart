package api_carrinho_produtos.dto;

import java.io.Serializable;

public record StockUpdateRequestDTO(Integer quantity) implements Serializable {
}
