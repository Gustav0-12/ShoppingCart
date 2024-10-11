package api_carrinho_produtos.repository;

import api_carrinho_produtos.entities.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Category, Long> {
}
