package api_carrinho_produtos.service;

import api_carrinho_produtos.dto.CategoryDTO;
import api_carrinho_produtos.entities.Category;
import api_carrinho_produtos.exception.EntityNotFoundException;
import api_carrinho_produtos.exception.UniqueViolationException;
import api_carrinho_produtos.repository.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CategoryService {

    @Autowired
    CategoryRepository repository;

    @CacheEvict(value = "categories", allEntries = true)
    public CategoryDTO save(Category category) {
        Optional<Category> categories = repository.findByName(category.getName());

        if(categories.isPresent()) {
            throw new UniqueViolationException("Categoria já foi registrada");
        }

        repository.save(category);

        return new CategoryDTO(category.getId(), category.getName());
    }

    @Cacheable(value = "categories")
    public List<CategoryDTO> findAll() {
        List<Category> categories =repository.findAll();
        return categories.stream().map(category -> new CategoryDTO(category.getId(), category.getName())).toList();
    }

    @Cacheable(value = "categories", key = "#id")
    public CategoryDTO findById(Long id) {
        Category category = repository.findById(id).orElseThrow(() -> new EntityNotFoundException("Category not found"));
        return new CategoryDTO(category.getId(), category.getName());
    }
}
