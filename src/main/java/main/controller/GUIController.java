package main.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

// import main.model.BookStoreModel;
import main.model.ShoppingCartModel;
import main.model.UserModel;
// import main.repository.BookStoreRepository;
import main.repository.ShoppingCartRepository;
import main.repository.UserRepository;

@Controller
public class GUIController {

    @Autowired
    // private final BookStoreRepository storeRepo;
    private final ShoppingCartRepository cartRepo;
    private final UserRepository userRepo;


    public GUIController(/*BookStoreRepository storeRepo,*/ ShoppingCartRepository cartRepo, UserRepository userRepo){
        // this.storeRepo = storeRepo;
        this.cartRepo = cartRepo;
        this.userRepo = userRepo;
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
     * @param title
     * @param model
     * @return book.html
     */
    @GetMapping("/book")
    public String book(@RequestParam("title") String title, Model model){
        model.addAttribute("path", "images/" + title);
        return "book";
    }



    /**
     * Cart Page.
     * This page will display the Cart, and the books it contains.
     * 
     * @param model
     * @return cart.html
     */
    @GetMapping("/cart")
    public String cartPage(Model model){
        ShoppingCartModel cart = cartRepo.findByName("Cart1");
        if (cart == null){
            cart = new ShoppingCartModel("Cart1");
            cartRepo.save(cart);
        }
        model.addAttribute("ShoppingCartModel", cart);
        
        //Add list of orders?


        return "index";
    }



    /**
     * User Page.
     * This page will display the User's Information.
     * 
     * @param model
     * @return user.html
     */
    @GetMapping("/user")
    public String userPage(Model model){
        UserModel user = userRepo.findByName("User1");
        if (user == null){
            user = new UserModel();
            user.setUsername("User1");
            userRepo.save(user);
        }
        model.addAttribute("UserModel", user);
        
        //User Info
        if ((user.getUsername()!=null) && (user.getEmail()!=null) && (user.getShippingAddress()!= null) ){
            model.addAttribute("username", user.getUsername());
            model.addAttribute("email", user.getEmail());
            model.addAttribute("shipAddress", user.getShippingAddress());
        }


        return "index";
    }


    
    /**
     * Admin Page.
     * This page will display the Admin's Information and priveledges.
     * 
     * @param model
     * @return admin.html
     */
    // @GetMapping("/admin")
    // public String adminPage(Model model){

    //     return "index";
    // }

}