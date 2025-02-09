package commerce.ShoppingCart.entities;

import jakarta.persistence.*;

import java.io.Serializable;

@Entity
@Table(name = "tb_category")
public class Category implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String name;
}
