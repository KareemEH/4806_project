package main.model;

import static org.junit.Assert.assertEquals;
import java.util.ArrayList;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class ShoppingCartModelTest {
    private BookModel book1;
    private BookModel book2;
    private List<BookModel> books;
    private  ShoppingCartModel cart;
    @Before
    public void setBook(){
        book1 = new BookModel(1L, "1234567890", "Book 1", "Description of book 1", "Author 1", "Publisher 1", 20.0f);
        book2 = new BookModel(2L, "0987654321", "Book 2", "Description of book 2", "Author 2", "Publisher 2", 25.0f);
        books = new ArrayList<>();
        books.add(book1);
        books.add(book2);
        cart = new ShoppingCartModel(1L, books, 0.0, 0.0);
        cart.calculateTotalPrice();
    }
    @After
    public void tearDown(){
        book1 = null;
        book2 = null;
        books = null;
    }
    @Test
    public void testCalculateTotalPrice() {
        assertEquals(45.0, cart.getPriceBeforeTax(), 0.01);
        assertEquals(50.85, cart.getPriceAfterTax(), 0.01);
    }
}