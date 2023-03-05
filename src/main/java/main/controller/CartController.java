package main.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import main.model.BookModel;
import main.repository.BookRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;



public class CartController {
    
    @Autowired
    private BookRepository bookRepository;

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
    @GetMapping("/{id}")
    public ResponseEntity<?> getBook(@PathVariable("bookName") String bookName, Model model) {
        BookModel book = bookRepository.findByName(bookName);
        if (book != null){
            return ResponseEntity.ok().body(book);
        } else{
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No book found with name of " + bookName);
        }
           
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

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteBook(@PathVariable ("bookName") String bookName) {
        BookModel book = bookRepository.findByName(bookName);
        if (book != null){
            //Delete using services
            return ResponseEntity.ok().body(book);
        } else{
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No book found with name of " + bookName);
        }
        
    }
}
