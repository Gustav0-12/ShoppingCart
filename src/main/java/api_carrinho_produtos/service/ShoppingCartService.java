package api_carrinho_produtos.service;

import api_carrinho_produtos.entities.CartProduct;
import api_carrinho_produtos.entities.Product;
import api_carrinho_produtos.entities.ShoppingCart;
import api_carrinho_produtos.entities.User;
import api_carrinho_produtos.repository.ProductRepository;
import api_carrinho_produtos.repository.ShoppingCartRepository;
import api_carrinho_produtos.repository.StockRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Optional;

@Service
public class ShoppingCartService {

    @Autowired
    ShoppingCartRepository repository;

    @Autowired
    ProductRepository productRepository;

    @Autowired
    CartProductService cartProductService;

    public ShoppingCart findByUserId(Long userId) {
        return repository.findCartByUserId(userId).orElseThrow(() -> new RuntimeException("Not found"));
    }

    @Transactional
    public ShoppingCart createCartForUser() {
        User authenticated = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Optional<ShoppingCart> shoppingCart = repository.findCartByUserId(authenticated.getId());

        if (shoppingCart.isPresent()) {
            return shoppingCart.get();
        } else {
            ShoppingCart newShoppingCart = new ShoppingCart();
            newShoppingCart.setCreationTime(Instant.now());
            newShoppingCart.setTotalPrice(BigDecimal.ZERO);
            newShoppingCart.setItems(new ArrayList<>());
            newShoppingCart.setUser(authenticated);
            return repository.save(newShoppingCart);
        }
    }

    @Transactional
    public ShoppingCart addProductForCart(Long id) {
        ShoppingCart shoppingCart = createCartForUser();
        Product product = productRepository.findById(id).orElseThrow(() -> new RuntimeException("Produto não encontrado"));
        Optional<CartProduct> item = cartProductService.findByCartIdAndProductId(shoppingCart.getId(), id);

        if (product.getStock().getQuantity() == 0) {
            throw new RuntimeException("Produto sem estoque");
        }

        CartProduct cartProduct;
        if (item.isEmpty()) {
            cartProduct = new CartProduct();
            cartProduct.setQuantity(1);
            cartProduct.setProduct(product);
            cartProduct.setTotalPrice(product.getPrice());
            cartProduct.setShoppingCart(shoppingCart);
            shoppingCart.getItems().add(cartProduct);
        } else {
            cartProduct = item.get();
            cartProduct.setQuantity(cartProduct.getQuantity() + 1);
            cartProduct.setTotalPrice(product.getPrice().multiply(new BigDecimal(cartProduct.getQuantity())));
        }
        shoppingCart.calculateTotalPrice();
        product.getStock().setQuantity(product.getStock().getQuantity() -1);
        productRepository.save(product);
        cartProductService.save(cartProduct);
        return repository.save(shoppingCart);
    }

    @Transactional
    public void removeProductFromCart(Long id) {
        User authenticated = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        ShoppingCart shoppingCart  = repository.findCartByUserId(authenticated.getId()).orElseThrow(() -> new RuntimeException("Not found"));
        Product product = productRepository.findById(id).orElseThrow(() -> new RuntimeException("Product not found"));
        Optional<CartProduct> item = cartProductService.findByCartIdAndProductId(shoppingCart.getId(), id);

        if (item.isEmpty()) {
            throw new RuntimeException("Produto não encontrado no carrinho");
        } else {
            CartProduct cartProduct = item.get();
            product.getStock().setQuantity(product.getStock().getQuantity() + cartProduct.getQuantity());
            shoppingCart.getItems().remove(cartProduct);
            shoppingCart.calculateTotalPrice();
            productRepository.save(product);
            cartProductService.delete(cartProduct.getId());
        }
        repository.save(shoppingCart);
    }
}
