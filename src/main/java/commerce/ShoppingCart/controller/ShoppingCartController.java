package commerce.ShoppingCart.controller;

import commerce.ShoppingCart.dto.ShoppingCartDTO;
import commerce.ShoppingCart.dto.UserResponseDTO;
import commerce.ShoppingCart.entities.ShoppingCart;
import commerce.ShoppingCart.service.ShoppingCartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/shoppingcart")
public class ShoppingCartController {

    @Autowired
    ShoppingCartService service;

    @GetMapping("/user/{userId}")
    public ResponseEntity<ShoppingCartDTO> findByUserId(@PathVariable Long userId) {
        ShoppingCartDTO shoppingCart = service.findByUserId(userId);
        return ResponseEntity.ok().body(shoppingCart);
    }

    @PostMapping("/{userId}/add/{productId}")
    public ResponseEntity<ShoppingCartDTO> addProductForCart(@PathVariable Long userId, @PathVariable Long productId) {
        ShoppingCart shoppingCart = service.addProductForCart(userId, productId);

        List<ShoppingCartDTO.CartProductsDTO> items = shoppingCart.getItems().stream().map(
                item -> new ShoppingCartDTO.CartProductsDTO(
                        item.getQuantity(),
                        item.getTotalPrice(),
                        item.getProduct()))
                .toList();
        UserResponseDTO user = new UserResponseDTO(shoppingCart.getUser().getId(), shoppingCart.getUser().getName(), shoppingCart.getUser().getEmail());

        return ResponseEntity.ok().body(new ShoppingCartDTO(shoppingCart.getId(), shoppingCart.getCreationTime(), shoppingCart.getTotalPrice(), items, user));
    }

    @DeleteMapping("/{userId}/remove/{productId}")
    public ResponseEntity<Void> removeProductFromCart(@PathVariable Long userId, @PathVariable Long productId) {
        service.removeProductFromCart(userId, productId);
        return ResponseEntity.noContent().build();
    }
}
