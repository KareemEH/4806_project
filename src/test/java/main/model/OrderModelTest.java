package main.model;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import static org.junit.Assert.*;
import static org.junit.Assert.assertNull;

public class OrderModelTest {
    private Map<BookModel, Integer> bookQuantityMap;
    UserModel user;
    private OrderModel order;
    private OrderModel defaultOrder;

    @Before
    public void setUp() {
        bookQuantityMap = new HashMap<>();
        bookQuantityMap.put(new BookModel(), 2);
        user = new UserModel();
        order = new OrderModel(1L, new Date(), 20.0, user, bookQuantityMap);
        defaultOrder = new OrderModel();
    }

    @After
    public void tearDown() {
        bookQuantityMap = null;
        user = null;
        order = null;
        defaultOrder = null;
    }

    @Test
    public void testOrderNoArgsConstructor() {
        assertNull(defaultOrder.getId());
        assertNull(defaultOrder.getDate());
        assertNull(defaultOrder.getTotalAmount());
        assertEquals(new HashMap<>(), defaultOrder.getBookQuantityMap());
        assertNull(defaultOrder.getUser());
    }

    @Test
    public void testOrderAllArgsConstructor() {
        assertEquals(1L, order.getId().longValue());
        assertNotNull(order.getDate());
        assertEquals(20.0, order.getTotalAmount(), 0);
        assertEquals(1, order.getBookQuantityMap().size());
        assertNotNull(order.getUser());
    }

    @Test
    public void testOrderSetters() {
        UserModel user = new UserModel();
        defaultOrder.setId(2L);
        defaultOrder.setDate(new Date());
        defaultOrder.setTotalAmount(25.0);
        defaultOrder.setBookQuantityMap(bookQuantityMap);
        defaultOrder.setUser(user);

        assertEquals(2L, defaultOrder.getId().longValue());
        assertNotNull(defaultOrder.getDate());
        assertEquals(25.0, defaultOrder.getTotalAmount(), 0);
        assertEquals(1, defaultOrder.getBookQuantityMap().size());
        assertEquals(user, defaultOrder.getUser());
    }

    @Test
    public void testOrderToString() {
        bookQuantityMap.clear();
        bookQuantityMap.put(new BookModel(1L, "1234567890123", "Test Book", "A test book", "Test Author", "Test Publisher", 10.0f), 2);
        String expectedString = "Order #: 1\n" +
                                "Date: " + order.getDate().toString() + "\n" +
                                "Books: Test Book (2) | \n" +
                                "Total Amount: 20.0\n";
        assertEquals(expectedString, order.toString());
    }
}
