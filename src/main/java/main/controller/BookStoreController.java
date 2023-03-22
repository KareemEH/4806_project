package main.controller;

import main.model.OrderModel;
import main.model.UserModel;
import main.repository.UserRepository;
import org.springframework.web.bind.annotation.*;
import main.model.BookModel;
import main.model.Credentials;
import main.repository.BookRepository;
import main.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

@RestController
public class BookStoreController {


    private final BookRepository bookRepository;
    private final UserRepository userRepository;
    private final UserService userService;

    @Autowired
    public BookStoreController(UserService userService, BookRepository bookRepository, UserRepository userRepository){
        this.userService = userService;
        this.bookRepository = bookRepository;
        this.userRepository = userRepository;
    }
    /**
     * Sign-Up Functionality. New User is checked to see if it is invalid (already exists).
     * New User is saved into UserRepository.
     * @return ResponseEntity
     */
    @PostMapping(value="/new_user", produces=MediaType.APPLICATION_JSON_VALUE)
    public String newUser(@RequestBody Credentials credentials){
        try{
            userService.createUser(credentials.getUsername(), credentials.getPassword());
        }catch (Exception e){
            return "{\"success\": false}";
        }
        
        return "{\"success\": true}";  
    }


    /**
     * Log-in Functionality. Using username and password, a matching user is requested.
     *
     * @return ResponseEntity
     */
    @PostMapping(value="/verify_login", produces=MediaType.APPLICATION_JSON_VALUE)
    public String verifyLogin(@RequestBody Credentials credentials){

        boolean valid = userService.verifyLogin(credentials.getUsername(), credentials.getPassword());
        if (valid){
            UserModel user = userRepository.findByUsername(credentials.getUsername());

            return "{\"success\": true, \"id\":"+user.getId()+"}";
        }
        return  "{\"success\": false}";
    }

    @GetMapping(value="getUserByUsername", produces=MediaType.APPLICATION_JSON_VALUE)
    public UserModel getUser(@RequestParam("username") String username) {
        UserModel User = userRepository.findByUsername(username);
        if(User.getUsername().equals(username)){
            return User;
        }
        return new UserModel();
    }

    @GetMapping(value="/frontPageBooks", produces=MediaType.APPLICATION_JSON_VALUE)
    public List<BookModel> frontPageBooks(){
        return bookRepository.findAll();
    }


    @GetMapping(value="bookByID", produces=MediaType.APPLICATION_JSON_VALUE)
    public BookModel getBooks(@RequestParam("id") int id) {
        for(BookModel b : bookRepository.findAll()){
            if(b.getId() == id){
                return b;
            }
        }
        return new BookModel();
    }

    @GetMapping(value="/getOrders", produces= MediaType.APPLICATION_JSON_VALUE)
    public ArrayList<String[]> getOrderBooks(@RequestParam("userid") Long userid) {
        UserModel user = userRepository.findAllById(Arrays.asList(userid)).get(0);
        List<OrderModel> orderList = user.getOrderList();
        ArrayList<String[]> bookList = new ArrayList<>();
        for (OrderModel order : orderList) {
            String[] arr = {order.getId().toString(), order.getDate().toString(), order.getTotalAmount().toString()};
            bookList.add(arr);
        }
        return bookList;
    }


    /**
     * Adding a book to database. TO be expanded upon later
     */
    @PostMapping("")
    public String addBook(@ModelAttribute BookModel book) {
        bookRepository.save(book);
        return "redirect:/books";
    }


}
