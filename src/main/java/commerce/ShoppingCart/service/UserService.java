package commerce.ShoppingCart.service;

import commerce.ShoppingCart.dto.UserRequestDTO;
import commerce.ShoppingCart.dto.UserResponseDTO;
import commerce.ShoppingCart.entities.User;
import commerce.ShoppingCart.exceptions.NotFoundException;
import commerce.ShoppingCart.exceptions.UniqueViolationException;
import commerce.ShoppingCart.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    @Autowired
    UserRepository repository;

    public UserResponseDTO saveUser(UserRequestDTO user) {
        Optional<User> existingUser = repository.findByEmail(user.email());
        if (existingUser.isPresent()) {
            throw new UniqueViolationException("Email já registrado");
        }

        User newUser = new User();
        newUser.setName(user.name());
        newUser.setEmail(user.email());
        newUser.setPassword(user.password());
        repository.save(newUser);

        return new UserResponseDTO(newUser.getId(), newUser.getName(), newUser.getEmail());
    }

    public List<UserResponseDTO> findAll() {
        List<User> users = repository.findAll();
        return users.stream().map(user -> new UserResponseDTO(user.getId(), user.getName(), user.getEmail())).toList();
    }

    public UserResponseDTO findById(Long id) {
        User user = repository.findById(id).orElseThrow(() -> new NotFoundException("Usuário não encontrado"));
        return new UserResponseDTO(user.getId(), user.getName(), user.getEmail());
    }
}
