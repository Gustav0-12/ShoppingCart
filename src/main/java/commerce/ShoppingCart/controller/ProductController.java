package commerce.ShoppingCart.controller;

import commerce.ShoppingCart.dto.ProductRequestDTO;
import commerce.ShoppingCart.dto.ProductResponseDTO;
import commerce.ShoppingCart.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/products")
public class ProductController {

    @Autowired
    ProductService service;

    @PostMapping("/add")
    public ResponseEntity<ProductResponseDTO> saveProduct(@RequestBody ProductRequestDTO product) {
        ProductResponseDTO newProduct = service.saveProduct(product);
        return ResponseEntity.ok().body(newProduct);
    }

    @GetMapping
    public ResponseEntity<List<ProductResponseDTO>> findAll() {
        List<ProductResponseDTO> products = service.findAll();
        return ResponseEntity.ok().body(products);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProductResponseDTO> findById(@PathVariable Long id) {
        ProductResponseDTO product = service.findById(id);
        return ResponseEntity.ok().body(product);
    }

    @DeleteMapping
    public void deleteProductById(@PathVariable Long id) {
        service.deleteProductById(id);
    }
}
