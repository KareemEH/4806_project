package main;

import com.fasterxml.jackson.databind.ObjectMapper;

import main.model.BookModel;
import main.model.Credentials;
import main.model.UserModel;
import main.repository.BookRepository;
import main.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class EndpointsTest {

    @Autowired
    private MockMvc mvc;

    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private UserRepository userRepository;

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
        BookModel book1 = new BookModel(1L, "9783161484100", "Test Book 1", "Test Description 1", "Test Author 1", "Test Publisher 1", "Test Genre 1", 19.99f,10);
        BookModel book2 = new BookModel(2L, "9783161484200", "Test Book 2", "Test Description 2", "Test Author 2", "Test Publisher 2", "Test Genre 2", 29.99f,10);
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
        BookModel book = new BookModel(1L, "9783161484100", "Test Book", "Test Description", "Test Author", "Test Publisher", "Test Genre",19.99f,10);
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

    @Test
    public void addBookAPITest() throws Exception {
        BookModel book = new BookModel(1L, "9783161484100", "Test Book", "Test Description", "Test Author", "Test Publisher", "Test Genre",19.99f,10);
        String json = asJsonString(book);

        mvc.perform(post("/")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    public static String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
