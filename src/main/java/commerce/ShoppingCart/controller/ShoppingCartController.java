package commerce.ShoppingCart.controller;

import commerce.ShoppingCart.dto.ShoppingCartDTO;
import commerce.ShoppingCart.dto.UserResponseDTO;
import commerce.ShoppingCart.entities.ShoppingCart;
import commerce.ShoppingCart.entities.User;
import commerce.ShoppingCart.service.ShoppingCartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/shoppingcart")
public class ShoppingCartController {

    @Autowired
    ShoppingCartService service;

    @GetMapping("/user")
    public ResponseEntity<ShoppingCartDTO> findUserCart(@AuthenticationPrincipal User usuarioAutenticado) {
        ShoppingCartDTO shoppingCart = service.findUserCart(usuarioAutenticado);
        return ResponseEntity.ok().body(shoppingCart);
    }

    @PostMapping("/add/{productId}")
    public ResponseEntity<ShoppingCartDTO> addProductForCart(@AuthenticationPrincipal User usuarioAutenticado, @PathVariable Long productId) {
        ShoppingCart shoppingCart = service.addProductForCart(usuarioAutenticado, productId);

        List<ShoppingCartDTO.CartProductsDTO> items = shoppingCart.getItems().stream().map(
                item -> new ShoppingCartDTO.CartProductsDTO(
                        item.getQuantity(),
                        item.getTotalPrice(),
                        item.getProduct()))
                .toList();
        UserResponseDTO user = new UserResponseDTO(shoppingCart.getUser().getId(), shoppingCart.getUser().getName(), shoppingCart.getUser().getEmail());

        return ResponseEntity.ok().body(new ShoppingCartDTO(shoppingCart.getId(), shoppingCart.getCreationTime(), shoppingCart.getTotalPrice(), items, user));
    }

    @DeleteMapping("/remove/{productId}")
    public ResponseEntity<Void> removeProductFromCart(@AuthenticationPrincipal User usuarioAutenticado, @PathVariable Long productId) {
        service.removeProductFromCart(usuarioAutenticado, productId);
        return ResponseEntity.noContent().build();
    }
}
