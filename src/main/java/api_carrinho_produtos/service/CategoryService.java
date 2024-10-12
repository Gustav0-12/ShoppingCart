package api_carrinho_produtos.service;

import api_carrinho_produtos.entities.Category;
import api_carrinho_produtos.exception.EntityNotFoundException;
import api_carrinho_produtos.repository.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryService {

    @Autowired
    CategoryRepository repository;

    public Category save(Category category) {
        return repository.save(category);
    }

    public List<Category> findAll() {
        return repository.findAll();
    }

    public Category findById(Long id) {
        return repository.findById(id).orElseThrow(() -> new EntityNotFoundException("Category not found"));
    }
}
