package commerce.ShoppingCart.service;

import commerce.ShoppingCart.dto.ProductRequestDTO;
import commerce.ShoppingCart.dto.ProductResponseDTO;
import commerce.ShoppingCart.entities.Category;
import commerce.ShoppingCart.entities.Product;
import commerce.ShoppingCart.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProductService {

    @Autowired
    ProductRepository repository;

    @Autowired
    CategoryService categoryService;

    public ProductResponseDTO saveProduct(ProductRequestDTO product) {
        Optional<Product> existingProduct = repository.findByName(product.name());
        Category category = categoryService.findById(product.categoryId());

        if (existingProduct.isPresent()) {
            throw new RuntimeException("Produto já registrado");
        }

        Product newProduct = new Product();
        newProduct.setName(product.name());
        newProduct.setDescription(product.description());
        newProduct.setPrice(product.price());
        newProduct.setCategory(category);
        repository.save(newProduct);

        return new ProductResponseDTO(newProduct.getId(), newProduct.getName(), newProduct.getDescription(), newProduct.getPrice(),newProduct.getCategory());
    }

    public List<ProductResponseDTO> findAll() {
        List<Product> products = repository.findAll();
        return products.stream().map(product -> new ProductResponseDTO(product.getId(), product.getName(), product.getDescription(), product.getPrice(), product.getCategory())).toList();
    }

    public ProductResponseDTO findById(Long id) {
        Product product = repository.findById(id).orElseThrow(() -> new RuntimeException("Produto não encontrado"));
        return new ProductResponseDTO(product.getId() ,product.getName(), product.getDescription(), product.getPrice(), product.getCategory());
    }

    public void deleteProductById(Long id) {
        repository.deleteById(id);
    }
}
