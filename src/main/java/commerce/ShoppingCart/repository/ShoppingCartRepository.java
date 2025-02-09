package commerce.ShoppingCart.repository;

import commerce.ShoppingCart.entities.ShoppingCart;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ShoppingCartRepository extends JpaRepository<ShoppingCart, Long> {
    Optional<ShoppingCart> findCartByUserId(Long userId);
}

