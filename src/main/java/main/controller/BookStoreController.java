package main.controller;

import org.springframework.web.bind.annotation.*;
import main.model.Credentials;
import org.springframework.http.MediaType;

import java.util.ArrayList;

import main.model.BookModel;

@RestController
public class BookStoreController {

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

    @GetMapping(value="/frontPageBooks", produces=MediaType.APPLICATION_JSON_VALUE)
    public ArrayList<BookModel> verifyLogin(){
        //TODO implement access to the actual model
        
        ArrayList<BookModel> books = new ArrayList<BookModel>();
        books.add(new BookModel(0L, "ISBN-GHI456", "1984", "A book about technological oppression", "George Orwell", "Secker & Warburg", 14.38F));
        books.add(new BookModel(1L, "ISBN-ABC789", "The Mist", "A mist rolls over a small town", "Stephen King", "Viking Press", 17.99F));
        books.add(new BookModel(2L, "ISBN-DEF123", "FARENHEIT 451", "A very warm temperature", "Ray Bradbury", "Ballantine Books", 21.00F));

        
        return books;
    }
}
