package main.model;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;
import java.util.HashMap;
import java.util.Map;

public class ShoppingCartModelTest {
    private Map<BookModel, Integer> bookQuantityMap;
    private ShoppingCartModel shoppingCart;
    private ShoppingCartModel defaultShoppingCart;

    @Before
    public void setUp() {
        bookQuantityMap = new HashMap<>();
        bookQuantityMap.put(new BookModel(), 2);
        shoppingCart = new ShoppingCartModel(1L, bookQuantityMap, 20.0);
        defaultShoppingCart = new ShoppingCartModel();
    }

    @After
    public void tearDown() {
        bookQuantityMap = null;
        shoppingCart = null;
        defaultShoppingCart = null;
    }

    @Test
    public void testCartNoArgsConstructor() {
        assertNull(defaultShoppingCart.getId());
        assertEquals(0.0, defaultShoppingCart.getTotalAmount(), 0);
        assertEquals(new HashMap<>(), defaultShoppingCart.getBookQuantityMap());
    }

    @Test
    public void testCartAllArgsConstructor() {
        assertEquals(1L, shoppingCart.getId().longValue());
        assertEquals(1, shoppingCart.getBookQuantityMap().size());
        assertEquals(20.0, shoppingCart.getTotalAmount(), 0);
    }

    @Test
    public void testCartSetters() {
        defaultShoppingCart.setId(2L);
        defaultShoppingCart.setBookQuantityMap(bookQuantityMap);
        defaultShoppingCart.setTotalAmount(25.0);

        assertEquals(2L, defaultShoppingCart.getId().longValue());
        assertEquals(1, defaultShoppingCart.getBookQuantityMap().size());
        assertEquals(25.0, defaultShoppingCart.getTotalAmount(), 0);
    }

    @Test
    public void testCartToString() {
        bookQuantityMap.clear();
        bookQuantityMap.put(new BookModel(1L, "1234567890123", "Test Book", "A test book", "Test Author", "Test Publisher", 10.0f), 2);
        String expectedString = "Shopping Cart #: 1\n" +
                "Books: Test Book (2) | \n" +
                "Total Amount: $20.0\n";
        assertEquals(expectedString, shoppingCart.toString());
    }
}