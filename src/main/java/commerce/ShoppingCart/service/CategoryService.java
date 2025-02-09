package commerce.ShoppingCart.service;

import commerce.ShoppingCart.entities.Category;
import commerce.ShoppingCart.exceptions.NotFoundException;
import commerce.ShoppingCart.exceptions.UniqueViolationException;
import commerce.ShoppingCart.repository.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CategoryService {

    @Autowired
    CategoryRepository repository;

    public Category saveCategory(Category category) {
        Optional<Category> existingCategory = repository.findByName(category.getName());

        if (existingCategory.isPresent()) {
            throw new UniqueViolationException("Categoria já registrada");
        }
        return repository.save(category);
    }

    public List<Category> findAll() {
        return repository.findAll();
    }

    public Category findById(Long id) {
        return repository.findById(id).orElseThrow(() -> new NotFoundException("Categoria não encontrada"));
    }

    public void deleteCategoryById(Long id) {
        repository.deleteById(id);
    }
}
