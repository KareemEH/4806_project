package main.model;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import java.util.ArrayList;
import java.util.List;
import static org.junit.Assert.*;

public class UserModelTest {
    private UserModel user;
    private UserModel defaultUser;
    UserModel newUser;
    @Before
    public void setUp() {
        user = new UserModel(1L, "test_user", "123qwe", "test_user@gmail.com", "123 Some Street", "468 Some Street", new ShoppingCartModel(), new ArrayList<OrderModel>());
        defaultUser = new UserModel();
        newUser = new UserModel("test_user2", "qwe123");
    }
    @After
    public void tearDown() {
        user = null;
        defaultUser = null;
        newUser = null;
    }

    @Test
    public void testUserNoArgsConstructor() {
        assertNull(defaultUser.getId());
        assertNull(defaultUser.getUsername());
        assertNull(defaultUser.getPassword());
        assertNull(defaultUser.getEmail());
        assertNull(defaultUser.getShippingAddress());
        assertNull(defaultUser.getBillingAddress());
        assertNull(defaultUser.getShoppingCart());
        assertNull(defaultUser.getOrderList());
    }

    @Test
    public void testUserAllArgsConstructor() {
        assertEquals(1L, user.getId().longValue());
        assertEquals("test_user", user.getUsername());
        assertEquals("123qwe", user.getPassword());
        assertEquals("test_user@gmail.com", user.getEmail());
        assertEquals("123 Some Street", user.getShippingAddress());
        assertEquals("468 Some Street", user.getBillingAddress());
        assertNotNull(user.getShoppingCart());
        assertNotNull(user.getOrderList());
    }

    @Test
    public void testUserLoginConstructor() {
        assertNull(newUser.getId());
        assertEquals("test_user2", newUser.getUsername());
        assertEquals("qwe123", newUser.getPassword());
        assertNull(newUser.getEmail());
        assertNull(newUser.getShippingAddress());
        assertNull(newUser.getBillingAddress());
        assertNull(newUser.getShoppingCart());
        assertNull(newUser.getOrderList());
    }

    @Test
    public void testUserModelSetters() {
        defaultUser.setId(2L);
        defaultUser.setUsername("test_user2");
        defaultUser.setPassword("123qwe123");
        defaultUser.setEmail("test_user2@gmail.com");
        defaultUser.setShippingAddress("456 Some Street");
        defaultUser.setBillingAddress("789 Some Street");
        ShoppingCartModel shoppingCart = new ShoppingCartModel();
        defaultUser.setShoppingCart(shoppingCart);
        List<OrderModel> orderList = new ArrayList<>();
        defaultUser.setOrderList(orderList);

        assertEquals(2L, defaultUser.getId().longValue());
        assertEquals("test_user2", defaultUser.getUsername());
        assertEquals("123qwe123", defaultUser.getPassword());
        assertEquals("test_user2@gmail.com", defaultUser.getEmail());
        assertEquals("456 Some Street", defaultUser.getShippingAddress());
        assertEquals("789 Some Street", defaultUser.getBillingAddress());
        assertEquals(shoppingCart, defaultUser.getShoppingCart());
        assertEquals(orderList, defaultUser.getOrderList());
    }

    @Test
    public void testUserToString() {
        String expectedString = "User #: 1\n" +
                "Username: test_user\n" +
                "Billing Address: 468 Some Street\n" +
                "Shipping Address: 123 Some Street\n";
        assertEquals(expectedString, user.toString());
    }
}