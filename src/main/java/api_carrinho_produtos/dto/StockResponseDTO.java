package api_carrinho_produtos.dto;
public record StockResponseDTO(
        Long id,
        Integer quantity,
        String product) {
}
