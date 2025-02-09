package commerce.ShoppingCart.repository;

import commerce.ShoppingCart.entities.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Long> {

}
