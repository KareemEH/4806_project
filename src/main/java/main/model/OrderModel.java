package main.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

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
    private UserModel user;
    @OneToMany(cascade = CascadeType.PERSIST, fetch = FetchType.LAZY)
    private List<BookModel> books;
    public String toString() {
        String str = "";
        for (int i = 0; i < books.size(); i++) {
            String book = books.get(i).getTitle();
            str += book + " | ";
        }
        return "Order #: " + id + "\n" + "Date: " + date.toString() + "\n" + "Books: " + str + "\n" + "Total Amount: " + totalAmount + "\n";
    }
}
