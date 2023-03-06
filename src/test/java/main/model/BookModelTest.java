package main.model;

import org.junit.Test;
import org.junit.Before;
import org.junit.After;
import static org.junit.Assert.*;

public class BookModelTest {
    private BookModel book;

    @Before
    public void setBook(){
        book = new BookModel(1L, "1234567890", "Title", "Description", "Author", "Publisher", 10.99f);
    }
    @After
    public void tearDown(){
        book = null;
    }
    @Test
    public void testBookModel() {
        assertEquals(1L, book.getId().longValue());
        assertEquals("1234567890", book.getIsbn());
        assertEquals("Title", book.getTitle());
        assertEquals("Description", book.getDescription());
        assertEquals("Author", book.getAuthor());
        assertEquals("Publisher", book.getPublisher());
        assertEquals(10.99f, book.getPrice(), 0.001f);
    }
}