package api_carrinho_produtos.service;

import api_carrinho_produtos.dto.ProductRequestDTO;
import api_carrinho_produtos.dto.ProductResponseDTO;
import api_carrinho_produtos.entities.Category;
import api_carrinho_produtos.entities.Product;
import api_carrinho_produtos.entities.Stock;
import api_carrinho_produtos.repository.CategoryRepository;
import api_carrinho_produtos.repository.ProductRepository;
import api_carrinho_produtos.repository.StockRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class ProductService {

    @Autowired
    CategoryRepository categoryRepository;

    @Autowired
    StockRepository stockRepository;

    @Autowired
    ProductRepository repository;

    public List<ProductResponseDTO> findAll() {
        List<Product> products = repository.findAll();
        return products.stream().map(product -> new ProductResponseDTO(product.getId(), product.getName(), product.getDescription(), product.getPrice(), product.getCategory())).collect(Collectors.toList());
    }

    public ProductResponseDTO findById(Long id) {
        Product product = repository.findById(id).orElseThrow(() -> new RuntimeException("Product not found"));
        return new ProductResponseDTO(product.getId(), product.getName(), product.getDescription(), product.getPrice(), product.getCategory());
    }

    public ProductResponseDTO create(ProductRequestDTO product) {
        Optional<Product> products = repository.findByName(product.name());
        Set<Category> categories = categoryRepository.findAllById(product.categoryId()).stream().collect(Collectors.toSet());

        if (products.isPresent()) {
            throw new RuntimeException("Esse produto já existe");
        }

        if (categories.isEmpty()) {
            throw new RuntimeException("Categoria inexistente");
        }

        Product newProduct = new Product();
        newProduct.setName(product.name());
        newProduct.setDescription(product.description());
        newProduct.setPrice(product.price());
        newProduct.getCategory().addAll(categories);

        Stock newStock = new Stock();
        newStock.setQuantity(50);
        newStock.setProduct(newProduct);
        newProduct.setStock(newStock);

        repository.save(newProduct);

        return new ProductResponseDTO(newProduct.getId(), newProduct.getName(), newProduct.getDescription(), newProduct.getPrice(), newProduct.getCategory());
    }
}
