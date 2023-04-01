package main.service;

import main.model.BookModel;
import java.util.Map;

public interface UserService {

    void createUser(String username, String password) throws Exception;
    boolean verifyLogin(String username, String password);
    boolean addToCart(Long userID, Long bookID, int quantity);
    boolean removeFromCart(Long userId, Long bookId);
    boolean createOrder(Long userid);
    double calculateTotalPrice(Map<BookModel, Integer> bookQuantityMap);
    int getBookQuantityInCart(Long userId, Long bookId);
}