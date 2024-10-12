package api_carrinho_produtos.service;

import api_carrinho_produtos.entities.CartProduct;
import api_carrinho_produtos.exception.EntityNotFoundException;
import api_carrinho_produtos.repository.CartProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CartProductService {

    @Autowired
    CartProductRepository repository;

    public CartProduct save(CartProduct cartProduct) {
        return repository.save(cartProduct);
    }

    public List<CartProduct> findAll() {
        return repository.findAll();
    }

    public CartProduct findById(Long id) {
        return repository.findById(id).orElseThrow(() -> new EntityNotFoundException("Not found"));
    }

    public Optional<CartProduct> findByCartIdAndProductId(Long cartId ,Long productId) {
        return repository.findByShoppingCartIdAndProductId(cartId, productId);
    }

    public void delete(Long id) {
        repository.deleteById(id);
    }
 }
