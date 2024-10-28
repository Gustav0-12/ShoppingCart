package api_carrinho_produtos.repository;

import api_carrinho_produtos.entities.CartProduct;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CartProductRepository extends JpaRepository<CartProduct, Long> {
    Optional<CartProduct> findByShoppingCartIdAndProductId(Long cartId, Long productId);
}
