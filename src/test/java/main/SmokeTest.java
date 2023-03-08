package main;

import main.controller.BookStoreController;
import main.controller.CartController;
import main.controller.GUIController;
import main.repository.BookRepository;
import main.repository.OrderRepository;
import main.repository.ShoppingCartRepository;
import main.repository.UserRepository;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
public class SmokeTest {

    @Autowired
    private BookStoreController bookStoreController;

    @Autowired
    private GUIController guiController;

    @Autowired
    private CartController cartController;

    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private ShoppingCartRepository shoppingCartRepository;

    @Autowired
    private UserRepository userRepository;

    @Test
    public void contextLoadsBookStoreController() throws Exception{
        assertThat(bookStoreController).isNotNull();
    }

    @Test
    public void contextLoadsGUIController() throws Exception{
        assertThat(guiController).isNotNull();
    }

    @Test
    public void contextLoadsCarteController() throws Exception{
        assertThat(cartController).isNotNull();
    }

    @Test
    public void contextLoadsBookRepository() throws Exception{
        assertThat(bookRepository).isNotNull();
    }

    @Test
    public void contextLoadsOrderRepository() throws Exception{
        assertThat(orderRepository).isNotNull();
    }

    @Test
    public void contextShoppingCartRepository() throws Exception{
        assertThat(shoppingCartRepository).isNotNull();
    }

    @Test
    public void contextLoadsUserRepository() throws Exception{
        assertThat(userRepository).isNotNull();
    }


}
