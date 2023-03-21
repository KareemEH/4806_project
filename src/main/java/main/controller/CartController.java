package main.controller;

import main.model.UserModel;
import main.repository.UserRepository;
import main.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import main.model.BookModel;
import main.repository.BookRepository;

import java.util.*;
import java.util.Map;

@RestController
public class CartController {
    

    private BookRepository bookRepository;
    private final UserService userService;
    private UserRepository userRepository;

    @Autowired
    public CartController(UserService userService, BookRepository bookRepository, UserRepository userRepository) {
        this.userService = userService;
        this.bookRepository = bookRepository;
        this.userRepository = userRepository;
    }


    @GetMapping(value="userCart", produces= MediaType.APPLICATION_JSON_VALUE)
    public Map<BookModel, Integer> getBooks(@RequestParam("userid") Long userid) {
        UserModel user = userRepository.findAllById(Arrays.asList(userid)).get(0);
        Map<BookModel, Integer> Books = user.getShoppingCart().getBookQuantityMap();

        return Books;
    }

    /**
     * Adding a book to Cart. TO be expanded upon later
     * @param userid
     * @param bookid
     * @return
     */
    @PostMapping("/tocart")
    public String addBook(@RequestParam("userid") Long userid, @RequestParam("bookid") Long bookid, @RequestParam("quantity") int quantity) {
        boolean addTo = userService.addToCart(userid,bookid, quantity);
        if (addTo){
            return  "{\"success\": true}";
        }
        return  "{\"success\": false}";
    }

}
