package api_carrinho_produtos.controller;

import api_carrinho_produtos.dto.StockResponseDTO;
import api_carrinho_produtos.dto.StockUpdateRequestDTO;
import api_carrinho_produtos.entities.Stock;
import api_carrinho_produtos.service.StockService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("api/stock")
public class StockController {

    @Autowired
    StockService service;

    @GetMapping
    public ResponseEntity<List<StockResponseDTO>> findAll() {
        List<Stock> stock = service.findAll();

        List<StockResponseDTO> stocks = stock.stream().map(
                stock1 -> new StockResponseDTO(
                        stock1.getId(),
                        stock1.getQuantity(),
                        stock1.getProduct().getName()))
                .toList();

        return ResponseEntity.ok(stocks);
    }

    @PostMapping("/update/{productId}")
    public ResponseEntity<StockResponseDTO> updateStock(@PathVariable Long productId, @RequestBody StockUpdateRequestDTO quantity) {
        Stock stock = service.updateStock(productId, quantity);
        return ResponseEntity.ok(new StockResponseDTO(stock.getId(), stock.getQuantity(), stock.getProduct().getName()));
    }
}
