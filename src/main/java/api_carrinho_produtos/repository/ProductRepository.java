package api_carrinho_produtos.repository;

import api_carrinho_produtos.entities.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface ProductRepository extends JpaRepository<Product, Long> {
    Optional<Product> findByName(String name);

    @Query("SELECT p FROM Product p JOIN FETCH p.category")
    List<Product> findAllProducts();
}
