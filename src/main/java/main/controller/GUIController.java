package main.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import main.repository.ShoppingCartRepository;
import main.repository.UserRepository;
import main.repository.BookRepository;

@Controller
public class GUIController {

    @Autowired
    private final ShoppingCartRepository cartRepo;
    private final UserRepository userRepo;
    private final BookRepository bookRepo;


    public GUIController(ShoppingCartRepository cartRepo, UserRepository userRepo, BookRepository bookRepo){
        
        this.cartRepo = cartRepo;
        this.userRepo = userRepo;
        this.bookRepo = bookRepo;
    }


    /**
     * Login and Registration Pages.
     * Users will log in or sign up to their accounts in these pages
     * 
     * @return login.html/register.html
     */
    @GetMapping("login")
    public String login(){
        return "login";
    }

    @GetMapping("register")
    public String register(){
        return "register";
    }



    /**
     * Home Page.
     * This page will display the Bookstore, and the books it contains.
     * 
     * @param model
     * @return index.html
     */
    @GetMapping("/")
    public String homePage(Model model){
        return "index";
    }



    /**
     * Book Page.
     * Selecting a book will display its information 
     * @param id
     * @param model
     * @return book.html
     */
    @GetMapping("/book")
    public String book(@RequestParam("id") Long id, Model model){
        model.addAttribute("id", id);
        return "book";
    }


}