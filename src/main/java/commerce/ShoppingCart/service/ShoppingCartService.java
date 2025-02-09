package commerce.ShoppingCart.service;

import commerce.ShoppingCart.dto.ShoppingCartDTO;
import commerce.ShoppingCart.dto.UserResponseDTO;
import commerce.ShoppingCart.entities.ShoppingCart;
import commerce.ShoppingCart.entities.User;
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

    CartProductsService cartProductsService;

    @Autowired
    UserRepository userRepository;

    @Autowired
    ProductRepository productRepository;

    public ShoppingCart createCartForUser(Long userId) {
        User user = userRepository.findById(userId).orElseThrow(() -> new RuntimeException("Usuário não encontrado"));
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

    public ShoppingCartDTO findByUserId(Long userId) {
        ShoppingCart shoppingCart = repository.findCartByUserId(userId).orElseThrow(() -> new RuntimeException("Carrinho não encontrado"));

        UserResponseDTO user = new UserResponseDTO(shoppingCart.getUser().getId() ,shoppingCart.getUser().getName(), shoppingCart.getUser().getEmail());
        List<ShoppingCartDTO.CartProductsDTO> items = shoppingCart.getItems().stream().map(item ->
                new ShoppingCartDTO.CartProductsDTO(item.getId(),
                        item.getQuantity(), item.getTotalPrice(),
                        item.getProduct())).toList();

        return new ShoppingCartDTO(shoppingCart.getId(), shoppingCart.getCreationTime(), shoppingCart.getTotalPrice(), user, items);
    }
}
