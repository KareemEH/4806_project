package main.model;

import static org.junit.Assert.assertEquals;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
public class OrderModelTest {
    private BookModel book1;
    private BookModel book2;
    private List<BookModel> books;
    private UserModel user;
    private OrderModel order;

    @Before
    public void setBook(){
        book1 = new BookModel(1L, "1234567890", "Book 1", "Description of book 1", "Author 1", "Publisher 1", 20.0f);
        book2 = new BookModel(2L, "0987654321", "Book 2", "Description of book 2", "Author 2", "Publisher 2", 25.0f);
        books = new ArrayList<>();
        books.add(book1);
        books.add(book2);
        user = new UserModel(1L, "testUser", "testUser@gmail.com", "shippingAddress", "billingAddress", null, null);
        order = new OrderModel(1L, new Date(), 0.0, user, books);
    }
    @After
    public void tearDown(){
        book1 = null;
        book2 = null;
        books = null;
        user = null;
        order = null;
    }
    @Test
    public void testOrderModel() {
        assertEquals(1L, (long)order.getId());
        assertEquals(user, order.getUser());
        assertEquals(books, order.getBooks());
        assertEquals(2, order.getBooks().size());
        assertEquals("Book 1", order.getBooks().get(0).getTitle());
        assertEquals("Book 2", order.getBooks().get(1).getTitle());
    }
}