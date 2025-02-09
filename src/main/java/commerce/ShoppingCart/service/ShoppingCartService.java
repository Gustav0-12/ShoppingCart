package commerce.ShoppingCart.service;

import commerce.ShoppingCart.dto.ShoppingCartDTO;
import commerce.ShoppingCart.dto.UserResponseDTO;
import commerce.ShoppingCart.entities.CartProducts;
import commerce.ShoppingCart.entities.Product;
import commerce.ShoppingCart.entities.ShoppingCart;
import commerce.ShoppingCart.entities.User;
import commerce.ShoppingCart.exceptions.NotFoundException;
import commerce.ShoppingCart.repository.ProductRepository;
import commerce.ShoppingCart.repository.ShoppingCartRepository;
import commerce.ShoppingCart.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ShoppingCartService {

    @Autowired
    ShoppingCartRepository repository;

    @Autowired
    CartProductsService cartProductsService;

    @Autowired
    UserRepository userRepository;

    @Autowired
    ProductRepository productRepository;

    public ShoppingCart createCartForUser(Long userId) {
        User user = userRepository.findById(userId).orElseThrow(() -> new NotFoundException("Usuário não encontrado"));
        Optional<ShoppingCart> existingShoppingCart = repository.findCartByUserId(user.getId());

        if (existingShoppingCart.isPresent()) {
            return existingShoppingCart.get();
        } else {
            ShoppingCart newShoppingCart = new ShoppingCart();
            newShoppingCart.setUser(user);
            newShoppingCart.setCreationTime(Instant.now());
            newShoppingCart.setItems(new ArrayList<>());
            newShoppingCart.setTotalPrice(BigDecimal.ZERO);
            return repository.save(newShoppingCart);
        }
    }

    public ShoppingCart addProductForCart(Long userId, Long productId) {
        ShoppingCart shoppingCart = createCartForUser(userId);
        Product product = productRepository.findById(productId).orElseThrow(() -> new NotFoundException("Produto não encontrado"));

        List<CartProducts> existingProductInShoppingCart = shoppingCart.getItems().stream().filter(cartProduct -> cartProduct.getProduct().getId().equals(product.getId())).toList();
        CartProducts cartProducts;
        if (existingProductInShoppingCart.isEmpty()) {
            cartProducts = new CartProducts();
            cartProducts.setShoppingCart(shoppingCart);
            cartProducts.setQuantity(1);
            cartProducts.setProduct(product);
            cartProducts.setTotalPrice(product.getPrice());
            shoppingCart.getItems().add(cartProducts);
        } else {
            cartProducts = existingProductInShoppingCart.get(0);
            cartProducts.setQuantity(cartProducts.getQuantity() + 1);
            cartProducts.setTotalPrice(product.getPrice().multiply(new BigDecimal(cartProducts.getQuantity())));
        }
        shoppingCart.CalculateTotalPrice();
        cartProductsService.save(cartProducts);
        return repository.save(shoppingCart);
    }

    public void removeProductFromCart(Long userId, Long productId) {
        ShoppingCart shoppingCart = repository.findCartByUserId(userId).orElseThrow(() -> new NotFoundException("Carrinho não encontrado"));
        Product product = productRepository.findById(productId).orElseThrow(() -> new NotFoundException("Produto não encontrado"));

        List<CartProducts> existingProductInShoppingCart = shoppingCart.getItems().stream().filter(cartProduct -> cartProduct.getProduct().getId().equals(product.getId())).toList();
        CartProducts cartProducts;
        if (existingProductInShoppingCart.isEmpty()) {
            throw new NotFoundException("Produto não encontrado");
        } else {
            cartProducts = existingProductInShoppingCart.get(0);
            shoppingCart.getItems().remove(cartProducts);
            shoppingCart.CalculateTotalPrice();
            cartProductsService.delete(cartProducts.getId());
            repository.save(shoppingCart);
        }
    }

    public ShoppingCartDTO findByUserId(Long userId) {
        ShoppingCart shoppingCart = repository.findCartByUserId(userId).orElse(createCartForUser(userId));

        UserResponseDTO user = new UserResponseDTO(shoppingCart.getUser().getId() ,shoppingCart.getUser().getName(), shoppingCart.getUser().getEmail());
        List<ShoppingCartDTO.CartProductsDTO> items = shoppingCart.getItems().stream().map(item ->
                new ShoppingCartDTO.CartProductsDTO(
                        item.getQuantity(),
                        item.getTotalPrice(),
                        item.getProduct())).
                toList();

        return new ShoppingCartDTO(shoppingCart.getId(), shoppingCart.getCreationTime(), shoppingCart.getTotalPrice(), items, user);
    }
}
