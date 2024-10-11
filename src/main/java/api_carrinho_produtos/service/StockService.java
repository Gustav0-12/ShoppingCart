package api_carrinho_produtos.service;
import api_carrinho_produtos.dto.StockUpdateRequestDTO;
import api_carrinho_produtos.entities.Stock;
import api_carrinho_produtos.repository.StockRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class StockService {

    @Autowired
    StockRepository repository;

    public Stock findById(Long id) {
        return repository.findById(id).orElseThrow(() -> new RuntimeException("Stock not found"));
    }

    public List<Stock> findAll() {
        return repository.findAll();
    }

    @Transactional
    public Stock updateStock(Long productId, StockUpdateRequestDTO data) {
       Stock productStock = repository.findByProductId(productId).orElseThrow(() -> new RuntimeException("Stock not found"));

       if (productStock.getQuantity() != null) {
           productStock.setQuantity(productStock.getQuantity() + data.quantity());
       }
       return repository.save(productStock);
    }

}
