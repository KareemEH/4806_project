package main.controller;

import org.springframework.web.bind.annotation.*;

import main.model.BookModel;
import main.model.Credentials;
import main.repository.BookRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;

@RestController
public class BookStoreController {

    @Autowired
    private BookRepository bookRepository;

    @PostMapping(value="/new_user", produces=MediaType.APPLICATION_JSON_VALUE)
    public String newUser(@RequestBody Credentials credentials){
        //TODO implement account creation in backend
        System.out.println(credentials.getUsername());
        System.out.println(credentials.getPassword());
        return "{\"success\": true}";
    }

    @PostMapping(value="/verify_login", produces=MediaType.APPLICATION_JSON_VALUE)
    public String verifyLogin(@RequestBody Credentials credentials){
        //TODO implement account creation in backend
        System.out.println(credentials.getUsername());
        System.out.println(credentials.getPassword());
        
        return "{\"success\": true}";
    }

    @GetMapping("")
    public String getBooks(Model model) {
        model.addAttribute("books", bookRepository.findAll());
        return "books";
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
