// package main.controller;

// import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.stereotype.Controller;
// import org.springframework.ui.Model;
// import org.springframework.web.bind.annotation.GetMapping;

// import main.model.BookStoreModel;
// import main.model.ShoppingCartModel;
// import main.model.UserModel;
// import main.repository.BookStoreRepository;
// import main.repository.ShoppingCartRepository;
// import main.repository.UserRepository;

// @Controller
// public class GUIController {

//     @Autowired
//     private final BookStoreRepository storeRepo;
//     private final ShoppingCartRepository cartRepo;
//     private final UserRepository userRepo;


//     public GUIController(BookStoreRepository storeRepo, ShoppingCartRepository cartRepo, UserRepository userRepo){
//         this.storeRepo = storeRepo;
//         this.cartRepo = cartRepo;
//         this.userRepo = userRepo;
//     }

//     /**
//      * Home Page.
//      * This page will display the Bookstore, and the books it contains.
//      * 
//      * @param model
//      * @return index.html
//      */
//     @GetMapping("/home")
//     public String homePage(Model model){
//         BookStoreModel bookStore = storeRepo.findByName("Store1");
//         if (bookStore == null){
//             bookStore = new BookStoreModel("Store1");
//             storeRepo.save(bookStore);
//         }
//         model.addAttribute("BookStoreModel", bookStore);
        
//         //Add list of books?


//         return "index";
//     }

//     /**
//      * Cart Page.
//      * This page will display the Cart, and the books it contains.
//      * 
//      * @param model
//      * @return index.html
//      */
//     @GetMapping("/cart")
//     public String cartPage(Model model){
//         ShoppingCartModel cart = cartRepo.findByName("Cart1");
//         if (cart == null){
//             cart = new ShoppingCartModel("Cart1");
//             cartRepo.save(cart);
//         }
//         model.addAttribute("ShoppingCartModel", cart);
        
//         //Add list of orders?


//         return "index";
//     }

//     /**
//      * User Page.
//      * This page will display the User's Information.
//      * 
//      * @param model
//      * @return index.html
//      */
//     @GetMapping("/user")
//     public String userPage(Model model){
//         UserModel user = userRepo.findByName("User1");
//         if (user == null){
//             user = new UserModel("User1");
//             userRepo.save(user);
//         }
//         model.addAttribute("UserModel", user);
        
//         //Add list of user information (UserName, Addresses...)?


//         return "index";
//     }

//     /**
//      * User Page.
//      * This page will display the User's Information.
//      * 
//      * @param model
//      * @return index.html
//      */
//     @GetMapping("/admin")
//     public String adminPage(Model model){

        
        


//         return "index";
//     }
// }
