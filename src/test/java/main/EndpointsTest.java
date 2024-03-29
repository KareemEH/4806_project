package main;

import com.fasterxml.jackson.databind.ObjectMapper;

import main.model.*;
import main.repository.BookRepository;
import main.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.env.Environment;
import org.springframework.http.MediaType;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@TestPropertySource(locations="classpath:application.test.properties")
public class EndpointsTest {

    @Autowired
    private MockMvc mvc;

    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private Environment env;

    @Test
    public void testPropertySource() {
        String dataSourceUrl = env.getProperty("spring.datasource.url");
        System.out.println("The value of spring.datasource.url is: " + dataSourceUrl);
    }

    @Test
    public void registerNewUserAPITest() throws Exception {
        if(userRepository.findByUsername("user")!=null){
            userRepository.delete(userRepository.findByUsername("user"));
        }
        Credentials credentials = new Credentials("user","password");
        mvc.perform(post("/new_user")
                        .content(asJsonString(credentials))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(MockMvcResultMatchers.jsonPath("$.success").value("true"));

    }

    @Test
    public void verifyLoginAPITest() throws Exception {
        if(userRepository.findByUsername("user")!=null){
            userRepository.delete(userRepository.findByUsername("user"));
        }
        Credentials credentials = new Credentials("user","password");
        mvc.perform(post("/new_user")
                .content(asJsonString(credentials))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON));

        mvc.perform(post("/verify_login")
                        .content(asJsonString(credentials))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(MockMvcResultMatchers.jsonPath("$.success").value("true"));
    }

    @Test
    public void getUserByUsernameAPITest() throws Exception {
        if(userRepository.findByUsername("testuser")!=null){
            userRepository.delete(userRepository.findByUsername("testuser"));
        }
        UserModel user = new UserModel("testuser", "testpassword");
        userRepository.save(user);

        mvc.perform(post("/getUserByUsername")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new Credentials("testuser", "testpassword").toJSON())
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(user.getId()));
    }

    @Test
    public void getFrontPageBooksAPITest() throws Exception {
        BookModel book1 = new BookModel(1L, "9783161484100", "Test Book 1", "Test Description 1", "Test Author 1", "Test Publisher 1", "Test Genre 1", 19.99f, "coverImage.png",10);
        BookModel book2 = new BookModel(2L, "9783161484200", "Test Book 2", "Test Description 2", "Test Author 2", "Test Publisher 2", "Test Genre 2", 29.99f, "coverImage.png",10);
        bookRepository.save(book1);
        bookRepository.save(book2);

        mvc.perform(get("/frontPageBooks")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].isbn").value(book1.getIsbn()))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].title").value(book1.getTitle()))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].description").value(book1.getDescription()))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].author").value(book1.getAuthor()))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].publisher").value(book1.getPublisher()))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].genre").value(book1.getGenre()))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].price").value(book1.getPrice()))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].id").value(book1.getId()))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].isbn").value(book2.getIsbn()))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].title").value(book2.getTitle()))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].description").value(book2.getDescription()))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].author").value(book2.getAuthor()))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].publisher").value(book2.getPublisher()))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].price").value(book2.getPrice()))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].genre").value(book2.getGenre()))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].id").value(book2.getId()))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].stock").value(book2.getStock()));
    }

    @Test
    public void getBookByIDAPITest() throws Exception {
        BookModel book = new BookModel(1L, "9783161484100", "Test Book", "Test Description", "Test Author", "Test Publisher", "Test Genre", 19.99f, "coverImage.png",10);
        bookRepository.save(book);

        mvc.perform(get("/bookByID")
                        .param("id", String.valueOf(book.getId()))
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(MockMvcResultMatchers.jsonPath("$.isbn").value(book.getIsbn()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.title").value(book.getTitle()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.description").value(book.getDescription()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.author").value(book.getAuthor()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.publisher").value(book.getPublisher()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.genre").value(book.getGenre()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.price").value(book.getPrice()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(book.getId()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.stock").value(book.getStock()));
    }

    // Test that valid "/restock" requests from admin are performed correctly
    @Test
    public void testValidRestockAPI() throws Exception {
        int quantityToAdd = 74;
        int initialQuantity = 10;
        BookModel book = new BookModel(1L, "9783161484100", "Test Book", "Test Description", "Test Author", "Test Publisher", "Test Genre", 19.99f, "coverImage.png", initialQuantity);
        BookModel book2 = new BookModel(2L, "9783161484200", "Test Book 2", "Test Description 2", "Test Author 2", "Test Publisher 2", "Test Genre 2", 29.99f, "coverImage.png",10);
        bookRepository.save(book);
        bookRepository.save(book2);

        // Make the request to restock
        mvc.perform(post("/restock")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new Credentials("Admin", "Admin").toJSON())
                        .param("bookId", String.valueOf(book.getId()))
                        .param("quantity", "" + quantityToAdd)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(MockMvcResultMatchers.jsonPath("$.success").value(true));

        // Make request to verify book stock has increased
        mvc.perform(get("/bookByID")
                        .param("id", String.valueOf(book.getId()))
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(MockMvcResultMatchers.jsonPath("$.isbn").value(book.getIsbn()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.title").value(book.getTitle()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.description").value(book.getDescription()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.author").value(book.getAuthor()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.publisher").value(book.getPublisher()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.genre").value(book.getGenre()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.price").value(book.getPrice()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(book.getId()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.stock").value(initialQuantity + quantityToAdd));

        // Make request to ensure other book's stock not affected
        mvc.perform(get("/bookByID")
                        .param("id", String.valueOf(book2.getId()))
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(MockMvcResultMatchers.jsonPath("$.isbn").value(book2.getIsbn()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.title").value(book2.getTitle()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.description").value(book2.getDescription()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.author").value(book2.getAuthor()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.publisher").value(book2.getPublisher()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.genre").value(book2.getGenre()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.price").value(book2.getPrice()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(book2.getId()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.stock").value(book2.getStock()));
    }

    // Test that you can't add stock without being admin
    @Test
    public void testInvalidRestockAPI() throws Exception {
        int quantityToAdd = 74;
        int initialQuantity = 10;
        BookModel book = new BookModel(1L, "9783161484100", "Test Book", "Test Description", "Test Author", "Test Publisher", "Test Genre", 19.99f, "coverImage.png", initialQuantity);
        bookRepository.save(book);

        // Make the request to restock with wrong password
        mvc.perform(post("/restock")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new Credentials("Admin", "WrongPassword").toJSON())
                        .param("bookId", String.valueOf(book.getId()))
                        .param("quantity", "" + quantityToAdd)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(MockMvcResultMatchers.jsonPath("$.success").value(false));

        // Make request to verify book stock has not changed
        mvc.perform(get("/bookByID")
                        .param("id", String.valueOf(book.getId()))
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(MockMvcResultMatchers.jsonPath("$.isbn").value(book.getIsbn()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.title").value(book.getTitle()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.description").value(book.getDescription()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.author").value(book.getAuthor()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.publisher").value(book.getPublisher()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.genre").value(book.getGenre()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.price").value(book.getPrice()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(book.getId()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.stock").value(book.getStock()));

        // Make the request to restock with Username (not the admin)
        mvc.perform(post("/restock")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new Credentials("NotAdmin", "Admin").toJSON())
                        .param("bookId", String.valueOf(book.getId()))
                        .param("quantity", "" + quantityToAdd)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(MockMvcResultMatchers.jsonPath("$.success").value(false));

        // Make request to verify book stock has not changed
        mvc.perform(get("/bookByID")
                        .param("id", String.valueOf(book.getId()))
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(MockMvcResultMatchers.jsonPath("$.isbn").value(book.getIsbn()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.title").value(book.getTitle()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.description").value(book.getDescription()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.author").value(book.getAuthor()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.publisher").value(book.getPublisher()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.genre").value(book.getGenre()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.price").value(book.getPrice()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(book.getId()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.stock").value(book.getStock()));
    }

    @Test
    public void addBookAPITest() throws Exception {
        BookModel book = new BookModel(1L, "9783161484100", "Test Book", "Test Description", "Test Author", "Test Publisher", "Test Genre", 19.99f, "coverImage.png",10);
        String json = asJsonString(book);

        mvc.perform(post("/")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    public void recommendationAPITest() throws Exception {
        //Creating some books
        BookModel book1 = new BookModel(1L, "9783161484100", "Test Book", "Test Description", "Test Author", "Test Publisher", "Test Genre", 19.99f, "coverImage.png",10);
        BookModel book2 = new BookModel(2L, "9783161484100", "Test Book", "Test Description", "Test Author", "Test Publisher", "Test Genre", 19.99f, "coverImage.png",10);
        BookModel book3 = new BookModel(3L, "9783161484100", "Test Book", "Test Description", "Test Author", "Test Publisher", "Test Genre", 19.99f, "coverImage.png",10);
        //Creating Users
        if(userRepository.findByUsername("testuser1")!=null){
            userRepository.delete(userRepository.findByUsername("testuser1"));
        }
        if(userRepository.findByUsername("testuser2")!=null){
            userRepository.delete(userRepository.findByUsername("testuser2"));
        }
        UserModel user1 = new UserModel(1L,"testuser1", "testpassword", "testemail","testaddr","testaddr",new ShoppingCartModel(), new ArrayList<>());
        UserModel user2 = new UserModel(2L,"testuser2", "testpassword", "testemail","testaddr","testaddr",new ShoppingCartModel(), new ArrayList<>());

        //Adding Orders
        Map<BookModel, Integer> user1Order = new HashMap<>();
        user1Order.put(book1,1);
        user1Order.put(book2,1);
        user1Order.put(book3,1);

        user1.getOrderList().add(new OrderModel(new Date(), 59.97f, user1, user1Order));
        userRepository.save(user1);

        Map<BookModel, Integer> user2Order = new HashMap<>();
        user2Order.put(book1,1);
        user2.getOrderList().add(new OrderModel(new Date(), 19.99f, user2, user2Order));
        userRepository.save(user2);

        //Checking if User2 will get the two unique books that user 1 ordered
        MvcResult result = mvc.perform(post("/recommendations").param("userid", "2"))
                .andExpect(status().isOk())
                .andReturn();
        MockMvcResultMatchers.jsonPath("$[0].id").value(2);
        MockMvcResultMatchers.jsonPath("$[1].id").value(3);
    }

    @Test
    public void updateBookEndpoint() throws Exception {
        String newTitle = "NEW TITLE";
        String newIsbn = "NEW ISBN";
        String newDescription = "NEW DESCRIPTION";
        String newAuthor = "NEW AUTHOR";
        String newPublisher = "NEW PUBLISHER";
        Float newPrice = new Float(123.45);
        Integer newStock = new Integer(789);
        BookModel book = new BookModel(1L, "9783161484100", "Test Book", "Test Description", "Test Author", "Test Publisher", "Test Genre", 19.99f, "coverImage.png",10);
        bookRepository.save(book);

        mvc.perform(post("/updateBook")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new Credentials("Admin", "Admin").toJSON())
                        .param("bookId", String.valueOf(book.getId()))
                        .param("title", newTitle)
                        .param("isbn", newIsbn)
                        .param("description", newDescription)
                        .param("author", newAuthor)
                        .param("publisher", newPublisher)
                        .param("price", newPrice.toString())
                        .param("stock", newStock.toString())
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(MockMvcResultMatchers.jsonPath("$.success").value(true));

        mvc.perform(get("/bookByID")
                        .param("id", String.valueOf(book.getId()))
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(MockMvcResultMatchers.jsonPath("$.isbn").value(newIsbn))
                .andExpect(MockMvcResultMatchers.jsonPath("$.title").value(newTitle))
                .andExpect(MockMvcResultMatchers.jsonPath("$.description").value(newDescription))
                .andExpect(MockMvcResultMatchers.jsonPath("$.author").value(newAuthor))
                .andExpect(MockMvcResultMatchers.jsonPath("$.publisher").value(newPublisher))
                .andExpect(MockMvcResultMatchers.jsonPath("$.genre").value(book.getGenre()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.price").value(newPrice))
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(book.getId()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.stock").value(newStock));
    }

    public static String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
