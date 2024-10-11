package api_carrinho_produtos.controller;

import api_carrinho_produtos.entities.Category;
import api_carrinho_produtos.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/categories")
public class CategoryController {

    @Autowired
    CategoryService service;

    @PostMapping("/add")
    public ResponseEntity<Category> create(@RequestBody Category category) {
        Category categories = service.save(category);
        return ResponseEntity.ok(categories);
    }

    @GetMapping
    public ResponseEntity<List<Category>> findAll() {
        List<Category> categories = service.findAll();
        return ResponseEntity.ok(categories);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Category> findById(@PathVariable Long id) {
        Category categories = service.findById(id);
        return ResponseEntity.ok(categories);
    }
}
