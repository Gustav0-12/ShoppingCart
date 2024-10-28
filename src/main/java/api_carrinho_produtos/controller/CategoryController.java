package api_carrinho_produtos.controller;

import api_carrinho_produtos.dto.CategoryDTO;
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
    public ResponseEntity<CategoryDTO> create(@RequestBody Category category) {
        CategoryDTO categories = service.save(category);
        return ResponseEntity.ok(categories);
    }

    @GetMapping
    public ResponseEntity<List<CategoryDTO>> findAll() {
        List<CategoryDTO> categories = service.findAll();
        return ResponseEntity.ok(categories);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CategoryDTO> findById(@PathVariable Long id) {
        CategoryDTO categories = service.findById(id);
        return ResponseEntity.ok(categories);
    }
}
