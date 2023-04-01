package main.controller;

import main.model.UserModel;
import main.repository.UserRepository;
import main.model.Credentials;
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
    

    private final BookRepository bookRepository;
    private final UserService userService;
    private final UserRepository userRepository;

    @Autowired
    public CartController(UserService userService, BookRepository bookRepository, UserRepository userRepository) {
        this.userService = userService;
        this.bookRepository = bookRepository;
        this.userRepository = userRepository;
    }

    @CrossOrigin
    @PostMapping(value="/getUserCart", produces= MediaType.APPLICATION_JSON_VALUE)
    public ArrayList<String[]> getBooks(@RequestParam("userid") Long userid, @RequestBody Credentials credentials) {
        ArrayList<String[]> bookList = new ArrayList<>();
        boolean valid = userService.verifyLogin(credentials.getUsername(), credentials.getPassword());
        
        if(valid){
            UserModel user = userRepository.findAllById(Arrays.asList(userid)).get(0);
            Map<BookModel, Integer> Books = user.getShoppingCart().getBookQuantityMap();
            for (Map.Entry<BookModel, Integer> entry : Books.entrySet()) {
                String[] arr = {entry.getKey().getTitle(), entry.getKey().getAuthor(), entry.getKey().getPrice().toString(), entry.getValue().toString(),  entry.getKey().getId().toString()};
                bookList.add(arr);
            }
        }

        return bookList; // empty could mean invalid credentials
    }

    /**
     * Adding a book to Cart. TO be expanded upon later
     * @param userid
     * @param bookId
     * @return
     */
    @CrossOrigin
    @PostMapping("/addToCart")
    public String addBook(@RequestParam("userid") Long userid, @RequestParam("bookId") Long bookId, @RequestParam("quantity") int quantity, @RequestBody Credentials credentials) {
        boolean valid = userService.verifyLogin(credentials.getUsername(), credentials.getPassword());

        if(valid){    
            boolean addTo = userService.addToCart(userid, bookId, quantity);
            if (addTo){
                return  "{\"success\": true}";
            }
        }

        return  "{\"success\": false}"; // could be because of invalid credentials
    }

    @CrossOrigin
    @DeleteMapping("/deleteFromCart")
    public String deleteCartItem(@RequestParam("userid") Long userId, @RequestParam("bookId") Long bookId, @RequestBody Credentials credentials) {
        boolean valid = userService.verifyLogin(credentials.getUsername(), credentials.getPassword());

        if(valid){
            boolean removeFrom = userService.removeFromCart(userId, bookId);
            if (removeFrom){
                return "{\"success\": true}";
            }
        }

        return "{\"success\": false}"; // could be because of invalid credentials
    }

    @CrossOrigin
    @PostMapping("/checkoutCart")
    public String checkout(@RequestParam("userid") Long userid, @RequestBody Credentials credentials) {
        boolean valid = userService.verifyLogin(credentials.getUsername(), credentials.getPassword());

        if(valid){
            boolean orderMade = userService.createOrder(userid);
            if (orderMade){
                return  "{\"success\": true}";
            }
        }

        return  "{\"success\": false}"; // could be because of invalid credentials
    }

    @CrossOrigin
    @PostMapping("/getBookQuantityInCart")
    public String getBookQuantityInCart(@RequestParam("userid") Long userid,@RequestParam("bookid") Long bookid, @RequestBody Credentials credentials){
        boolean valid = userService.verifyLogin(credentials.getUsername(), credentials.getPassword());

        if(valid){
            return "{\"quantity\": " + userService.getBookQuantityInCart(userid,bookid)+ "}";
        }

        return "{\"quantity\": -1}";
    }

}
