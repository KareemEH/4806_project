package main.service;

import main.model.BookModel;
import main.model.ShoppingCartModel;
import main.model.UserModel;
import main.repository.BookRepository;
import main.repository.ShoppingCartRepository;
import main.repository.UserRepository;
import org.h2.engine.User;
import org.springframework.stereotype.Service;

import java.awt.print.Book;
import java.util.Map;
import java.util.Optional;


@Service
public class UserServiceImpl implements UserService{

    private final UserRepository userRepo;
    private final ShoppingCartRepository cartRepo;
    private final BookRepository bookRepo;

    public UserServiceImpl(UserRepository userRepo, ShoppingCartRepository cartRepo, BookRepository bookRepo){
        this.userRepo = userRepo;
        this.cartRepo = cartRepo;
        this.bookRepo = bookRepo;
    }

    @Override
    public void createUser(String username, String password) throws Exception {
        if(userRepo.findByUsername(username) == null){
            UserModel userModel = new UserModel(username, password);
            userRepo.save(userModel);
        }
        else{
            throw new Exception("Username already exists in repository");
        }
    }

    @Override
    public boolean verifyLogin(String username, String password){
        UserModel userModel = userRepo.findByUsername(username);
        if(userModel != null){
            return(userModel.getPassword().equals(password));
        }
        return false;
    }

    @Override
    public boolean addToCart(Long userID, Long bookID, int quantity) {
        Optional<UserModel> userModel = userRepo.findById(userID);
        Optional<BookModel> bookModel = bookRepo.findById(bookID);
        if(userModel.isPresent() && bookModel.isPresent()){
            ShoppingCartModel cart = userModel.get().getShoppingCart();
            Map<BookModel, Integer> bookQuantityMap  = cart.getBookQuantityMap();
            bookQuantityMap.put(bookModel.get(), quantity);
            cart.setBookQuantityMap(bookQuantityMap);
            cart.setTotalAmount(cart.getTotalAmount() + 1);

            cartRepo.save(cart);
            userRepo.save(userModel.get());
            return true;
        }
        return false;
    }

    @Override
    public ShoppingCartModel getCartContents(Long userID) {
        Optional<UserModel> userModel = userRepo.findById(userID);
        if(userModel.isPresent()){
            return userModel.get().getShoppingCart();
        }else{
            return null;
        }
    }

}
