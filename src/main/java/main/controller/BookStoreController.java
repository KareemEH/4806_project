package main.controller;

import java.io.File;
import main.model.OrderModel;
import main.model.UserModel;
import main.repository.UserRepository;
import org.springframework.web.bind.annotation.*;
import main.model.BookModel;
import main.model.Credentials;
import main.repository.BookRepository;
import main.service.UserService;
import main.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import java.nio.file.Files;
import java.nio.file.Paths;

import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;

@RestController
public class BookStoreController {


    private final BookRepository bookRepository;
    private final UserRepository userRepository;
    private final UserService userService;
    private final BookService bookService;

    @Autowired
    public BookStoreController(UserService userService, BookRepository bookRepository, UserRepository userRepository, BookService bookService){
        this.userService = userService;
        this.bookRepository = bookRepository;
        this.userRepository = userRepository;
        this.bookService = bookService;
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

    @CrossOrigin
    @PostMapping("/updateBook")
    public String updateBook(@RequestParam("bookId") Long bookId, @RequestParam("title") String title, @RequestParam("isbn") String isbn, @RequestParam("description") String description, @RequestParam("author") String author, @RequestParam("publisher") String publisher, @RequestParam("price") Float price, @RequestParam("stock") Integer stock, @RequestBody Credentials credentials) {
        if(!credentials.getUsername().equals("Admin")){
            System.out.println("Restock failed: not signed in as admin");
            return "{\"success\": false}";
        }

        boolean valid = userService.verifyLogin(credentials.getUsername(), credentials.getPassword());

        if(valid){    
            System.out.println("Updating book info");
            
            if(bookService.updateBook(bookId, title, isbn, description, author, publisher, price, stock)){
                return "{\"success\": true}";
            }
        }

        return "{\"success\": false}"; // could be because of invalid credentials
    }



    @CrossOrigin
    @PostMapping("/restock")
    public String restock(@RequestParam("bookId") Long bookId, @RequestParam("quantity") int quantity, @RequestBody Credentials credentials) {
        if(!credentials.getUsername().equals("Admin")){
            System.out.println("Restock failed: not signed in as admin");
            return "{\"success\": false}";
        }

        boolean valid = userService.verifyLogin(credentials.getUsername(), credentials.getPassword());

        if(valid){
            
            if(bookService.restockBook(bookId, quantity)){
                return "{\"success\": true}";
            }
        }

        return "{\"success\": false}"; // could be because of invalid credentials
    }

    @CrossOrigin
    @GetMapping(value="/getBookCover", produces=MediaType.APPLICATION_OCTET_STREAM_VALUE)
    public @ResponseBody byte[] getBookCover(@RequestParam("filename") String filename){
        try{
            return Files.readAllBytes(Paths.get(System.getProperty("user.dir") + "/coverImages/" + filename));
        }
        catch(Exception e){
            e.printStackTrace();
        }

        return null;
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

    @CrossOrigin
    @PostMapping(value="getUserByUsername", produces=MediaType.APPLICATION_JSON_VALUE)
    public UserModel getUser(@RequestBody Credentials credentials) {
        
        boolean valid = userService.verifyLogin(credentials.getUsername(), credentials.getPassword());

        if(valid){
            UserModel User = userRepository.findByUsername(credentials.getUsername());
            if(User.getUsername().equals(credentials.getUsername())){
                return User;
            }
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

    @CrossOrigin
    @PostMapping(value="addBookToStore", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> addCoverToBook(@RequestParam("username") String username, @RequestParam("password") String password, @RequestParam("isbn") String isbn, @RequestParam("title") String title, @RequestParam("description") String description, @RequestParam("author") String author, @RequestParam("publisher") String publisher, @RequestParam("genre") String genre, @RequestParam("price") float price, @RequestParam("stock") Integer stock, @RequestParam("cover") MultipartFile cover) {
        if(!username.equals("Admin")){
            System.out.println("Add book failed: Not the admin");
            // return "{\"success\": false}";
            // return new ResponseEntity<>( "{\"success\": false}", HttpStatus.BAD_REQUEST);
            return ResponseEntity.status(302).header("Location", "/addBook?failed=true").body("{\"success\": false}");
        }

        if(!userService.verifyLogin(username, password)){
            System.out.println("Add book failed: incorrect password");
            // return "{\"success\": false}";
            // return new ResponseEntity<>( "{\"success\": false}", HttpStatus.BAD_REQUEST);
            return ResponseEntity.status(302).header("Location", "/addBook?failed=true").body("{\"success\": false}");
        }

        
        try{
            cover.transferTo(new File(System.getProperty("user.dir") + "/coverImages/" + cover.getOriginalFilename()));
            this.bookService.addBook(isbn, title, description, author, publisher, genre, price, cover.getOriginalFilename(), stock);
        }
        catch(Exception e){
            System.out.println(e);
            System.out.println("err saving pic");
        }

        // return "{\"success\": true}";
        // return new ResponseEntity<>( "{\"success\": true}", HttpStatus.OK);
        return ResponseEntity.status(302).header("Location", "/addBook?succeeded=true").body("{\"success\": true}");
    }

    @CrossOrigin
    @PostMapping(value="/getOrders", produces= MediaType.APPLICATION_JSON_VALUE)
    public ArrayList<String[]> getOrderBooks(@RequestParam("userid") Long userid, @RequestBody Credentials credentials) {
        ArrayList<String[]> bookList = new ArrayList<>();
        boolean valid = userService.verifyLogin(credentials.getUsername(), credentials.getPassword());

        if(valid){
            UserModel user = userRepository.findAllById(Arrays.asList(userid)).get(0);
            List<OrderModel> orderList = user.getOrderList();
            for (OrderModel order : orderList) {
                String[] arr = {order.getId().toString(), order.getDate().toString(), order.getTotalAmount().toString()};
                bookList.add(arr);
            }
        }

        return bookList; // returning empty list might mean error
    }

    /**
     * Adding a book to database. TO be expanded upon later
     */
    @PostMapping("")
    public String addBook(@ModelAttribute BookModel book) {
        bookRepository.save(book);
        return "redirect:/books";
    }

    @PostMapping(value = "/recommendations",  produces= MediaType.APPLICATION_JSON_VALUE)
    public List<BookModel> getRecommendedBooks(@RequestParam("userid") Long userid){

        return userService.getRecommendedBooks(userid);
    }

}
