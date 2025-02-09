package commerce.ShoppingCart.service;

import commerce.ShoppingCart.dto.UserRequestDTO;
import commerce.ShoppingCart.dto.UserResponseDTO;
import commerce.ShoppingCart.entities.User;
import commerce.ShoppingCart.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    @Autowired
    UserRepository repository;

    public UserResponseDTO saveUser(UserRequestDTO user) {
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
        User user = repository.findById(id).orElseThrow(() -> new RuntimeException("Usuário não encontrado"));
        return new UserResponseDTO(user.getId(), user.getName(), user.getEmail());
    }
}
