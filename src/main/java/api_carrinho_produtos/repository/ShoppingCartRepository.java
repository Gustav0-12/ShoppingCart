package api_carrinho_produtos.repository;

import api_carrinho_produtos.entities.ShoppingCart;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ShoppingCartRepository extends JpaRepository<ShoppingCart, Long> {
    Optional<ShoppingCart> findCartByUserId(Long userId);
}
