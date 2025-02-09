package commerce.ShoppingCart.repository;

import commerce.ShoppingCart.entities.CartProducts;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CartProductsRepository extends JpaRepository<CartProducts, Long> {
}
