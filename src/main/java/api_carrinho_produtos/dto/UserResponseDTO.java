package api_carrinho_produtos.dto;

import java.io.Serializable;

public record UserResponseDTO(Long id, String name, String email) implements Serializable {
}
