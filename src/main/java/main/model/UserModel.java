package main.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Entity
@Table(name = "Users")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserModel implements Serializable {
    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private Long id;
    private String username;
    private String password;
    private String email;
    private String shippingAddress;
    private String billingAddress;
    @OneToOne(cascade = CascadeType.PERSIST, fetch = FetchType.EAGER)
    private ShoppingCartModel shoppingCart;
    @OneToMany(cascade = CascadeType.PERSIST, fetch = FetchType.LAZY)
    @Fetch(value = FetchMode.SUBSELECT)
    private List<OrderModel> orderList;

    public UserModel(String username, String password){
        this.username = username;
        this.password = password;
        this.shoppingCart = new ShoppingCartModel();
    }

    public boolean hasOrdered(BookModel book){
        for (OrderModel order : orderList){
            Map<BookModel, Integer> bookQuantityMap = order.getBookQuantityMap();
            for (Map.Entry<BookModel, Integer> entry : bookQuantityMap.entrySet()) {
                if (entry.getKey() == book){
                    return true;
                }
            }
        }
        return false;
    }
    public List<BookModel> getAllPurchasedBooks() {
        List<BookModel> allBooks = new ArrayList<>();
        for (OrderModel order : orderList){
            Map<BookModel, Integer> bookQuantityMap = order.getBookQuantityMap();
            for (Map.Entry<BookModel, Integer> entry : bookQuantityMap.entrySet()) {
                allBooks.add(entry.getKey());
            }
        }
        return allBooks;
    }

    // toString
    public String toString() {
        return "User #: " + id + "\n" + "Username: " + username + "\n" + "Billing Address: " + billingAddress + "\n" + "Shipping Address: " + shippingAddress + "\n";
    }
}
