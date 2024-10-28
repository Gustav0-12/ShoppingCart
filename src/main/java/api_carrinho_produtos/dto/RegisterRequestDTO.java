package api_carrinho_produtos.dto;

import api_carrinho_produtos.entities.enums.UserRole;

public record RegisterRequestDTO(String name, String email, String password, UserRole userRole) {
}
