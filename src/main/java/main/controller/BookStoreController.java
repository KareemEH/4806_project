package main.controller;

import org.springframework.web.bind.annotation.*;
import main.model.Credentials;
import org.springframework.http.MediaType;

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

    // @GetMapping(value="/frontPageBooks", produces=MediaType.APPLICATION_JSON_VALUE)
    // public ArrayList< verifyLogin(){
    //     //TODO implement account creation in backend
    //     System.out.println(credentials.getUsername());
    //     System.out.println(credentials.getPassword());
        
    //     return "{\"success\": true}";
    // }
}
