package commerce.ShoppingCart.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.Instant;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "tb_shopping_cart")
public class ShoppingCart implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Instant creationTime;
    private BigDecimal totalPrice;
    @ManyToOne
    private User user;
    @OneToMany(mappedBy = "shoppingCart")
    private List<CartProducts> items;

    public void CalculateTotalPrice() {
        totalPrice = BigDecimal.ZERO;
        for (CartProducts item : items) {
            totalPrice = totalPrice.add(item.getProduct().getPrice().multiply(new BigDecimal(item.getQuantity())));
        }
    }
}
