package api_carrinho_produtos.service;

import api_carrinho_produtos.entities.Category;
import api_carrinho_produtos.exception.EntityNotFoundException;
import api_carrinho_produtos.exception.UniqueViolationException;
import api_carrinho_produtos.repository.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CategoryService {

    @Autowired
    CategoryRepository repository;

    @CachePut(value = "categories", key = "#category.id")
    public Category save(Category category) {
        Optional<Category> categories = repository.findByName(category.getName());

        if(categories.isPresent()) {
            throw new UniqueViolationException("Categoria já foi registrada");
        }

        return repository.save(category);
    }

    @Cacheable(value = "categories")
    public List<Category> findAll() {
        return repository.findAll();
    }

    @Cacheable(value = "categories", key = "#id")
    public Category findById(Long id) {
        return repository.findById(id).orElseThrow(() -> new EntityNotFoundException("Category not found"));
    }
}
