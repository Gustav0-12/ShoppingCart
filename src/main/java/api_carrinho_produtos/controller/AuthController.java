package api_carrinho_produtos.controller;

import api_carrinho_produtos.dto.LoginRequestDTO;
import api_carrinho_produtos.dto.RegisterRequestDTO;
import api_carrinho_produtos.dto.UserResponseDTO;
import api_carrinho_produtos.entities.User;
import api_carrinho_produtos.exception.UniqueViolationException;
import api_carrinho_produtos.exception.WrongCredentialsException;
import api_carrinho_produtos.security.TokenService;
import api_carrinho_produtos.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    UserService service;

    @Autowired
    BCryptPasswordEncoder passwordEncoder;

    @Autowired
    TokenService tokenService;

    @PostMapping("/login")
    public ResponseEntity login(@RequestBody LoginRequestDTO data) {
        User user = service.findByEmail(data.email()).orElseThrow(() -> new RuntimeException("Email not found"));

        if (passwordEncoder.matches(data.password(), user.getPassword())) {
            String token = tokenService.generateToken(user);
            return ResponseEntity.ok().body(token);
        } else {
            throw new WrongCredentialsException("Senha incorreta");
        }
    }

    @PostMapping("/register")
    public ResponseEntity<UserResponseDTO> register(@RequestBody RegisterRequestDTO data) {
        Optional<User> user = service.findByEmail(data.email());

        if (!user.isEmpty()) {
            throw new UniqueViolationException("Usúario já cadastrado");
        }

        User newUser = new User();
        newUser.setName(data.name());
        newUser.setEmail(data.email());
        newUser.setPassword(passwordEncoder.encode(data.password()));
        newUser.setUserRole(data.userRole());
        service.save(newUser);
        return ResponseEntity.ok(new UserResponseDTO(newUser.getId(), newUser.getName(), newUser.getEmail()));
    }
}
