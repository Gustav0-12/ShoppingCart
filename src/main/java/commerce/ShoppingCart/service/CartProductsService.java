package commerce.ShoppingCart.service;

import commerce.ShoppingCart.entities.CartProducts;
import commerce.ShoppingCart.repository.CartProductsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CartProductsService {

    @Autowired
    CartProductsRepository repository;

    public CartProducts save(CartProducts cartProduct) {
        return repository.save(cartProduct);
    }

    public List<CartProducts> findAll() {
        return repository.findAll();
    }

    public CartProducts findById(Long id) {
        return repository.findById(id).orElseThrow(() -> new RuntimeException("Not found"));
    }

    public void delete(Long id) {
        repository.deleteById(id);
    }

}
