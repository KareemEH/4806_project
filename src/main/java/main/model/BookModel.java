package main.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "Books")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class BookModel implements Serializable {

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Long id;
    private String isbn;
    private String title;
    private String description;
    private String author;
    private String publisher;
}
