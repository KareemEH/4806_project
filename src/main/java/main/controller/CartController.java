package main.controller;

import main.model.UserModel;
import main.repository.UserRepository;
import main.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import main.model.BookModel;
import main.repository.BookRepository;

import java.util.HashMap;
import java.util.Map;

@RestController
public class CartController {
    

    private BookRepository bookRepository;
    private final UserService userService;
    private UserRepository userRepository;

    @Autowired
    public CartController(UserService userService) {
        this.userService = userService;
    }


    @GetMapping(value="userCart", produces= MediaType.APPLICATION_JSON_VALUE)
    public Map<BookModel, Integer> getBooks(@RequestParam("userid") long userid) {
        UserModel user = userRepository.findById(userid);
        Map<BookModel, Integer> Books = user.getShoppingCart().getBookQuantityMap();

        return Books;
    }

    /**
     * Adding a book to Cart. TO be expanded upon later
     * @param userid
     * @param bookid
     * @return
     */
    @PostMapping("")
    public String addBook(@RequestParam("userid") long userid, @RequestParam("bookid") long bookid, @RequestParam("quantity") int quantity) {
        boolean addTo = userService.addtoCart(userid,bookid, quantity);
        if (addTo){
            return  "{\"success\": true}";
        }
        return  "{\"success\": false}";
    }

}
