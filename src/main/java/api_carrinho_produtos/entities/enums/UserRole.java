package api_carrinho_produtos.entities.enums;

public enum UserRole {
    ADMIN("admin"),
    COMMON("common");

    public String userRole;

    UserRole(String userRole) {
        this.userRole = userRole;
    }

    public String getUserRole() {
        return userRole;
    }
}
