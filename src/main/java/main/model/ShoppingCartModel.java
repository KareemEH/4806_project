package main.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import javax.persistence.*;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

@Entity
@Table(name = "ShoppingCarts")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ShoppingCartModel implements Serializable {
    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private Long id;
    @ElementCollection(fetch = FetchType.LAZY)
    @CollectionTable(name = "ShoppingCartBooks", joinColumns = @JoinColumn(name = "shopping_cart_id"))
    @MapKeyJoinColumn(name = "book_id")
    @Column(name = "quantity")
    private Map<BookModel, Integer> bookQuantityMap = new HashMap<>();
    private double totalAmount;

    // toString
    public String toString() {
        String str = "";
        for (Map.Entry<BookModel, Integer> entry : bookQuantityMap.entrySet()) {
            String book = entry.getKey().getTitle();
            Integer quantity = entry.getValue();
            str += book + " (" + quantity + ") | ";
        }
        return "Shopping Cart #: " + id + "\n" + "Books: " + str + "\n" + "Total Amount: $" + totalAmount + "\n";
    }
}
