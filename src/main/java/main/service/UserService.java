package main.service;

import main.model.ShoppingCartModel;

public interface UserService {

    void createUser(String username,String password) throws Exception;
    boolean verifyLogin(String username, String password);

    boolean addToCart(Long userID, Long bookID, int quantity );

    ShoppingCartModel getCartContents(Long userID);
}
