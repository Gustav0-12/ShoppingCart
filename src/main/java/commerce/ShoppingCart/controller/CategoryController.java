package commerce.ShoppingCart.controller;

import commerce.ShoppingCart.entities.Category;
import commerce.ShoppingCart.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/category")
public class CategoryController {

    @Autowired
    CategoryService service;

    @PostMapping("/add")
    public ResponseEntity<Category> saveCategory(@RequestBody Category category) {
        Category newCategory = service.saveCategory(category);
        return ResponseEntity.ok().body(newCategory);
    }

    @GetMapping
    public ResponseEntity<List<Category>> findAll() {
        List<Category> categories = service.findAll();
        return ResponseEntity.ok().body(categories);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Category> findById(@PathVariable Long id) {
        Category category = service.findById(id);
        return ResponseEntity.ok().body(category);
    }

    @DeleteMapping
    public void deleteCategoryById(@PathVariable Long id) {
        service.deleteCategoryById(id);
    }
}
