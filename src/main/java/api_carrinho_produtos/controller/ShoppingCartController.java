package api_carrinho_produtos.controller;

import api_carrinho_produtos.dto.ShoppingCartDTO;
import api_carrinho_produtos.dto.UserResponseDTO;
import api_carrinho_produtos.entities.ShoppingCart;
import api_carrinho_produtos.service.ShoppingCartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/shoppingcart")
public class ShoppingCartController {

    @Autowired
    ShoppingCartService service;

    @GetMapping("/{userId}")
    public ResponseEntity<ShoppingCartDTO> findByUserId(@PathVariable Long userId) {
        ShoppingCart shoppingCart = service.findByUserId(userId);

        List<ShoppingCartDTO.CartProductDTO> items = shoppingCart.getItems().stream().map
                (item -> new ShoppingCartDTO.CartProductDTO(
                        item.getId(),
                        item.getQuantity(),
                        item.getTotalPrice(),
                        item.getProduct()))
                .toList();

        UserResponseDTO user = new UserResponseDTO(
                shoppingCart.getUser().getId(),
                shoppingCart.getUser().getName(),
                shoppingCart.getUser().getEmail());

        return ResponseEntity.ok(new ShoppingCartDTO(shoppingCart.getId(), shoppingCart.getCreationTime(), shoppingCart.getTotalPrice(), items, user));
    }

    @PostMapping("/add/{productId}")
    public ResponseEntity<ShoppingCartDTO> addProductForCart(@PathVariable Long productId) {
        ShoppingCart shoppingCart = service.addProductForCart(productId);

        List<ShoppingCartDTO.CartProductDTO> items = shoppingCart.getItems().stream().map
                (item -> new ShoppingCartDTO.CartProductDTO(
                        item.getId(),
                        item.getQuantity(),
                        item.getTotalPrice(),
                        item.getProduct()))
                .toList();

        UserResponseDTO user = new UserResponseDTO(
                shoppingCart.getUser().getId(),
                shoppingCart.getUser().getName(),
                shoppingCart.getUser().getEmail());

        return ResponseEntity.ok(new ShoppingCartDTO(shoppingCart.getId(), shoppingCart.getCreationTime(), shoppingCart.getTotalPrice(), items, user));
    }

    @DeleteMapping("/remove/{productId}")
    public ResponseEntity<Void> removeProductFromCart(@PathVariable Long productId) {
        service.removeProductFromCart(productId);
        return ResponseEntity.noContent().build();
    }

}
