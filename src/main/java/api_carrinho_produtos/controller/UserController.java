package api_carrinho_produtos.controller;

import api_carrinho_produtos.dto.UserResponseDTO;
import api_carrinho_produtos.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("api/users")
public class UserController {

    @Autowired
    UserService service;

    @GetMapping
    public ResponseEntity<List<UserResponseDTO>> findAll() {
        List<UserResponseDTO> users = service.findAll();
        return ResponseEntity.ok(users);
    }

    @GetMapping("/{userId}")
    public ResponseEntity<UserResponseDTO> findById(@PathVariable Long userId) {
        UserResponseDTO user = service.findById(userId);
        return ResponseEntity.ok(user);
    }
}
