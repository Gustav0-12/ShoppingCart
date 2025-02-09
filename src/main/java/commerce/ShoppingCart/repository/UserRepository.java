package commerce.ShoppingCart.repository;

import commerce.ShoppingCart.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
}
