package main.controller;

import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import main.model.BookModel;
import main.model.Credentials;
import main.model.UserModel;
import main.repository.BookRepository;
import main.service.UserService;

import org.apache.catalina.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;

import java.net.URI;
import java.util.ArrayList;

import main.model.BookModel;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;

@RestController
public class BookStoreController {

    @Autowired
    private BookRepository bookRepository;
    private UserService userService;

    public BookStoreController(UserService userService){
        this.userService = userService;
    }
    /**
     * Sign-Up Functionality. New User is checked to see if it is invalid (already exists).
     * New User is saved into UserRepository.
     * @param user
     * @return ResponseEntity
     */
    @PostMapping(value="/new_user", produces=MediaType.APPLICATION_JSON_VALUE)
    public String newUser(@RequestBody Credentials credentials){   
        UserModel newUser;
        try{
            newUser = userService.createUser(credentials.getUsername(), credentials.getPassword());       
        }catch (Exception e){
            e.printStackTrace();
            return "{\"success\": false}";
        }
        
        return "{\"success\": true}";  
    }


    /**
     * Log-in Functionality. Using username and password, a matching user is requested.
     * @param username
     * @param password
     * @return ResponseEntity
     */
    @PostMapping(value="/verify_login", produces=MediaType.APPLICATION_JSON_VALUE)
    public String verifyLogin(@RequestBody Credentials credentials){
        boolean user = userService.verifyLogin(credentials.getUsername(), credentials.getPassword());
        if (user) {
            return  "{\"success\": true}";
        } else {
            return  "{\"success\": false}";
        }
        
    }

    @GetMapping(value="/frontPageBooks", produces=MediaType.APPLICATION_JSON_VALUE)
    public ArrayList<BookModel> verifyLogin(){
        //TODO implement access to the actual model
        
        ArrayList<BookModel> books = new ArrayList<BookModel>();
        books.add(new BookModel(0L, "ISBN-GHI456", "1984", "A book about technological oppression", "George Orwell", "Secker & Warburg", 14.38F));
        books.add(new BookModel(1L, "ISBN-ABC789", "The Mist", "A mist rolls over a small town", "Stephen King", "Viking Press", 17.99F));
        books.add(new BookModel(2L, "ISBN-DEF123", "FARENHEIT 451", "A very warm temperature", "Ray Bradbury", "Ballantine Books", 21.00F));

        
        return books;
    }


    @GetMapping("")
    public String getBooks(Model model) {
        model.addAttribute("books", bookRepository.findAll());
        return "books";
    }


    /**
     * Adding a book to database. TO be expanded upon later
     * @param book
     * @return
     */
    @PostMapping("")
    public String addBook(@ModelAttribute BookModel book) {
        bookRepository.save(book);
        return "redirect:/books";
    }



    /**
     * Update to use service instead
     * @param bookName
     * @param model
     * @return
     */
    //Pending Service Implimentaion
    // @GetMapping("/{id}")
    // public ResponseEntity<?> getBook(@PathVariable("bookName") String bookName, Model model) {
    //     BookModel book = bookRepository.findByName(bookName);
    //     if (book != null){
    //         return ResponseEntity.ok().body(book);
    //     } else{
    //         return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No book found with name of " + bookName);
    //     }
           
    // }

    // @PutMapping("/{id}")
    // public String updateBook(@PathVariable Long id, @ModelAttribute BookModel book) {
    //     BookModel existingBook = bookRepository.findById(id);
    //     if (existingBook != null) {
    //         existingBook.setTitle(book.getTitle());
    //         existingBook.setDescription(book.getDescription());
    //         existingBook.setAuthor(book.getAuthor());
    //         existingBook.setPublisher(book.getPublisher());
    //         existingBook.setPrice(book.getPrice());
    //         bookRepository.save(existingBook);
    //     }
    //     return "redirect:/books";
    // }

    //Pending Service Implimentaion
    // @DeleteMapping("/{id}")
    // public ResponseEntity<?> deleteBook(@PathVariable ("bookName") String bookName) {
    //     BookModel book = bookRepository.findByName(bookName);
    //     if (book != null){
    //         //Delete using services
    //         return ResponseEntity.ok().body(book);
    //     } else{
    //         return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No book found with name of " + bookName);
    //     }
        
    // }


}
