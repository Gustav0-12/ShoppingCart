package api_carrinho_produtos.dto;

import api_carrinho_produtos.entities.Product;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.List;

public record ShoppingCartDTO(
        Long id,
        Instant creationTime,
        BigDecimal totalPrice,
        List<CartProductDTO> items,
        UserResponseDTO user) {

   public record CartProductDTO(
           Long id,
           Integer quantity,
           BigDecimal totalPrice,
           Product product
   ) {
   }
}
