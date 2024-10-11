## SHOPPINGCART
Este é um projeto de um sistema de carrinho de compras, desenvolvido em Java utilizando o framework Spring e banco de dados MySQL. Ele permite o gerenciamento de produtos, estoque e clientes, facilitando o processo de compra.

## Funcionalidades

- Cadastro de usuários
- Autenticação de usuários com tokens JWT
- Adicionar, remover e atualizar produtos no carrinho
- Gerenciamento de estoque e produtos

## Tecnologias Utilizadas

- **Java**: Linguagem de programação utilizada para o backend
- **Spring Boot**: Framework para criação de aplicações Java baseadas em Spring
- **Spring Security**: Para autenticação e autorização de usuários
- **JWT (JSON Web Token)**: Para autenticação de API com tokens
- **Hibernate**: ORM para gerenciamento do banco de dados
- **MySQL**: Banco de dados relacional utilizado
- **Maven**: Gerenciador de dependências e build
- **Git**: Controle de versão

## Requisitos

- Java 17+
- Maven 3.8.1+
- MySQL 8+
- Postman (opcional, para testar as APIs)

## Instalação e Execução

### 1. Clonar o repositório
```
git clone https://github.com/Gustav0-12/ShoppingCart.git
```
### 2. Configurar o banco de dados
Certifique-se de ter o MySQL instalado e rodando. Crie um banco de dados chamado shopping_cart:
```
CREATE DATABASE shopping_cart;
```
Atualize as credenciais do banco de dados no arquivo src/main/resources/application.properties    
```
spring.datasource.url=jdbc:mysql://localhost:3306/shopping_cart
spring.datasource.username=seu_usuario
spring.datasource.password=sua_senha
```
### 3. Compilar e rodar a aplicação
No diretório do projeto, execute os seguintes comandos para compilar e rodar o projeto:
```
mvn clean install
mvn spring-boot:run
```
## ENDPOINTS
### Usuários

``` Registrar usuário: POST /api/auth/register ```
```Json
{
    "name":"nome",
    "email":"email@example.com",
    "password":"senha",
    "userRole":"ADMIN/COMMON"
}
```
``` Logar usuário: POST /api/auth/login  ```
```Json
{
    "email":"email@example.com",
    "password":"senha"
}
```
``` Encontrar todos os usuário: GET /api/users  ```
```Json

[
    {
        "id": 1,
        "name": "username",
        "email": "email@example.com"
    }
]
```

### Categorias
``` Adicionar categoria: POST api/categories/add ```
```Json
{
    "name":"nome_categoria"
}
```
``` Encontrar todas as categorias: GET api/categories ```
```Json
[
    {
        "id": 1,
        "name": "Tecnologia"
    },
    {
        "id": 2,
        "name": "Teste"
    }
]
```
### Produtos
``` Adicionar produto: POST api/products/add ```
```Json
{
    "name":"nome_produto",
    "description":"descrição_produto",
    "price":"300",
    "categoryId":[1]
}
```
``` Encontrar todos os produtos: GET api/products ```
```Json
 "id": 1,
        "name": "nome_produto",
        "description": "descrição_produto",
        "price": 300,
        "category": [
            {
                "id": 1,
                "name": "Tecnologia"
            }
        ]
```
### Carrinho de compras
``` Adicionar produto no carrinho: POST api/shoppingcart/add/{productId} ```

``` Remover produto do carrinho: DELETE api/shoppingcart/remove/{productId} ```

``` Encontrar carrinho pelo id do usuário: GET api/shoppingcart/{userId} ```
```Json
{
    "id": 1,
    "creationTime": "2024-10-11T14:06:05.895013Z",
    "totalPrice": 0.00,
    "items": [],
    "user": {
        "id": 1,
        "name": "username",
        "email": "email@example.com"
    }
}
```
### Estoque
``` Encontrar todos os estoques: GET api/stock ```
```Json
 {
        "id": 2,
        "quantity": 55,
        "product": "Celular"
    },
    {
        "id": 3,
        "quantity": 50,
        "product": "Ventilador"
    },
    {
        "id": 4,
        "quantity": 50,
        "product": "Teste"
    }
```
``` Atualizar estoque: POST api/stock/update/{productId} ```
```Json

{
    "quantity":"5"
}
```




