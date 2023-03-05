package main.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

@Entity
@Table(name = "ShoppingCarts")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ShoppingCartModel implements Serializable {
    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private Integer id;
    @OneToMany(cascade = CascadeType.PERSIST, fetch = FetchType.LAZY)
    private List<BookModel> bookList;
    private double priceBeforeTax;
    private double priceAfterTax;

    // toString
    public String toString() {
        return "Shopping Cart #: " + id + "\n" + "Price Before Tax: $" + priceBeforeTax + "\n" + "Price After Tax: $" + priceAfterTax + "\n";
    }

    public void calculateTotalPrice() {
        double total = 0.0;
        for (BookModel book : bookList) {
            total += book.getPrice();
        }
        this.priceBeforeTax = total;
        this.priceAfterTax = total * 1.13;
    }
}
