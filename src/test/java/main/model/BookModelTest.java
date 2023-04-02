package main.model;

import org.junit.Test;
import org.junit.Before;
import org.junit.After;
import static org.junit.Assert.*;

public class BookModelTest {
    private BookModel book;
    private BookModel defaultBook;

    @Before
    public void setUp() {
        book = new BookModel(1L, "1234567890123", "Title", "Description", "Author", "Publisher", "Genre",10.99f,10);
        defaultBook = new BookModel();
    }
    @After
    public void tearDown() {
        book = null;
        defaultBook = null;
    }
    @Test
    public void testBookNoArgsConstructor() {
        assertNull(defaultBook.getId());
        assertNull(defaultBook.getIsbn());
        assertNull(defaultBook.getTitle());
        assertNull(defaultBook.getDescription());
        assertNull(defaultBook.getAuthor());
        assertNull(defaultBook.getPublisher());
        assertNull(defaultBook.getGenre());
        assertNull(defaultBook.getPrice());
    }

    @Test
    public void testBookAllArgsConstructor() {
        assertEquals(1L, book.getId().longValue());
        assertEquals("1234567890123", book.getIsbn());
        assertEquals("Title", book.getTitle());
        assertEquals("Description", book.getDescription());
        assertEquals("Author", book.getAuthor());
        assertEquals("Publisher", book.getPublisher());
        assertEquals("Genre", book.getGenre());
        assertEquals(10.99f, book.getPrice(), 0.001f);
    }

    @Test
    public void testBookSetters() {
        defaultBook.setId(2L);
        defaultBook.setIsbn("0987654321987");
        defaultBook.setTitle("New Title");
        defaultBook.setDescription("New Description");
        defaultBook.setAuthor("New Author");
        defaultBook.setPublisher("New Publisher");
        defaultBook.setGenre("New Genre");
        defaultBook.setPrice(20.99f);
        defaultBook.setStock(10);

        assertEquals(2L, defaultBook.getId().longValue());
        assertEquals("0987654321987", defaultBook.getIsbn());
        assertEquals("New Title", defaultBook.getTitle());
        assertEquals("New Description", defaultBook.getDescription());
        assertEquals("New Author", defaultBook.getAuthor());
        assertEquals("New Publisher", defaultBook.getPublisher());
        assertEquals("New Genre", defaultBook.getGenre());
        assertEquals(20.99f, defaultBook.getPrice(), 0.001f);
        assertEquals(10,defaultBook.getStock(),0);
    }
}
