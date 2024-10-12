package api_carrinho_produtos.service;

import api_carrinho_produtos.dto.UserResponseDTO;
import api_carrinho_produtos.entities.User;
import api_carrinho_produtos.exception.EntityNotFoundException;
import api_carrinho_produtos.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UserService {

    @Autowired
    UserRepository repository;

    public User save(User user) {
        return repository.save(user);
    }

    public List<UserResponseDTO> findAll() {
        List<User> users = repository.findAll();
        return users.stream().map(user -> new UserResponseDTO(user.getId(), user.getName(), user.getEmail())).collect(Collectors.toList());
    }

    public UserResponseDTO findById(Long id) {
        User user = repository.findById(id).orElseThrow(() -> new EntityNotFoundException("User not found"));
        return new UserResponseDTO(user.getId(), user.getName(), user.getEmail());
    }

    public Optional<User> findByEmail(String email) {
        return repository.findByEmail(email);
    }
}
