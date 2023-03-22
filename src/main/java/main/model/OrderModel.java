package main.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Entity
@Table(name = "Orders")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class OrderModel implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @Temporal(TemporalType.DATE)
    private Date date;
    private Double totalAmount;
    @ManyToOne
    @JoinColumn(name = "user_id")
    @JsonIgnore
    private UserModel user;
    @ElementCollection(fetch = FetchType.LAZY)
    @CollectionTable(name = "OrderBooks", joinColumns = @JoinColumn(name = "order_id"))
    @MapKeyJoinColumn(name = "book_id")
    @Column(name = "quantity")
    private Map<BookModel, Integer> bookQuantityMap = new HashMap<>();

    public OrderModel(Date date, double totalAmount, UserModel user, Map<BookModel, Integer> bookQuantityMap) {
        this.date = date;
        this.totalAmount = totalAmount;
        this.user = user;
        this.bookQuantityMap = bookQuantityMap;
    }

    public String toString() {
        String str = "";
        for (Map.Entry<BookModel, Integer> entry : bookQuantityMap.entrySet()) {
            String book = entry.getKey().getTitle();
            Integer quantity = entry.getValue();
            str += book + " (" + quantity + ") | ";
        }
        return "Order #: " + id + "\n" + "Date: " + date.toString() + "\n" + "Books: " + str + "\n" + "Total Amount: " + totalAmount + "\n";
    }
}