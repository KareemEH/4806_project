package main.service;

import main.model.BookModel;
import main.model.OrderModel;
import main.model.ShoppingCartModel;
import main.model.UserModel;
import main.repository.BookRepository;
import main.repository.OrderRepository;
import main.repository.ShoppingCartRepository;
import main.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.*;


@Service
public class UserServiceImpl implements UserService{

    private final UserRepository userRepo;
    private final ShoppingCartRepository cartRepo;
    private final OrderRepository orderRepo;
    private final BookRepository bookRepo;

    public UserServiceImpl(UserRepository userRepo, ShoppingCartRepository cartRepo, OrderRepository orderRepo, BookRepository bookRepo){
        this.userRepo = userRepo;
        this.cartRepo = cartRepo;
        this.orderRepo = orderRepo;
        this.bookRepo = bookRepo;
        //initializeUsers();
    }

    public void initializeUsers(){
        try{
            createUser("Admin", "Admin");
            createUser("Khalid", "Kana'An");
            createUser("Mohamed", "Abdalla");
            createUser("Kareem", "El-Hajjar");
            createUser("Evan", "Lloyd");
            createUser("Brandon", "Mercer");
        }
        catch(Exception e){
            e.printStackTrace();
            System.exit(-1);
        }
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
            if (bookQuantityMap.containsKey(bookModel.get())){
                bookQuantityMap.put(bookModel.get(), bookQuantityMap.get(bookModel.get()) + quantity);
                cart.setBookQuantityMap(bookQuantityMap);
            }else{
                bookQuantityMap.put(bookModel.get(), quantity);
                cart.setBookQuantityMap(bookQuantityMap);
            }

            cartRepo.save(cart);
            userRepo.save(userModel.get());
            return true;
        }
        return false;
    }

    @Override
    public boolean createOrder(Long userID) {
        Optional<UserModel> user = userRepo.findById(userID);
        if (user.isPresent()) {
            ShoppingCartModel cart = user.get().getShoppingCart();
            List<OrderModel> userOrders = user.get().getOrderList();
            Map<BookModel, Integer> bookQuantityMap = cart.getBookQuantityMap();
            OrderModel order;
            if (!bookQuantityMap.isEmpty()) {
                for(BookModel book : bookQuantityMap.keySet()){
                    book.setStock(book.getStock() - bookQuantityMap.get(book));
                    bookRepo.save(book);
                }
                order = new OrderModel(new Date(), calculateTotalPrice(bookQuantityMap), user.get(), bookQuantityMap);
                userOrders.add(order);
                bookQuantityMap = new HashMap<>();
                cart.setBookQuantityMap(bookQuantityMap); //clear cart
            } else {
                return false;
            }
            orderRepo.save(order);
            cartRepo.save(cart);
            return true;
        }
        return false;
    }

    public double calculateTotalPrice(Map<BookModel, Integer> bookQuantityMap) {
        double totalPrice = 0.0;
        for (Map.Entry<BookModel, Integer> entry : bookQuantityMap.entrySet()) {
            BookModel book = entry.getKey();
            Integer quantity = entry.getValue();
            double price = book.getPrice();
            totalPrice += quantity * price;
        }
        return Math.round(totalPrice * 100.0) / 100.0;
    }

    @Override
    public boolean removeFromCart(Long userID, Long bookID){
        Optional<UserModel> user = userRepo.findById(userID);
        Optional<BookModel> book = bookRepo.findById(bookID);

        if(user.isPresent() && book.isPresent()){
            ShoppingCartModel cart = user.get().getShoppingCart();
            Map<BookModel, Integer> bookQuantityMap  = cart.getBookQuantityMap();
            bookQuantityMap.remove(book.get());
            cartRepo.save(cart);
            System.out.print("testing testing testing");
            return true;
        }
        return false;
    }

    @Override
    public int getBookQuantityInCart(Long userId, Long bookId){
        BookModel book = bookRepo.findById(bookId).get();
        Map<BookModel, Integer> bookQuantityMap = userRepo.findById(userId).get().getShoppingCart().getBookQuantityMap();
        if(bookQuantityMap.get(book) == null){
            return 0;
        } else {
            return bookQuantityMap.get(book);
        }
    }

    @Override
    public List<BookModel> getRecommendedBooks(Long userId) {
        Optional<UserModel> currentUser = userRepo.findById(userId);
        List<UserModel> allUsers = userRepo.findAllWithOrders();
        Set<BookModel> recommendedBooks = new HashSet<>();

        if(currentUser.isPresent() && !currentUser.get().getOrderList().isEmpty()){
            Map<UserModel, Double> jaccardDistances = new HashMap<>();
            for (UserModel user : allUsers) {
                if (user.getId() != userId) {
                    double jaccardDistance = calculateJaccardDistance(currentUser.get(), user);
                    jaccardDistances.put(user, jaccardDistance);
                }
            }
            List<UserModel> similarUsers = getSimilarUsers(jaccardDistances);

            for (UserModel similarUser : similarUsers) {
                List<OrderModel> orders = similarUser.getOrderList();
                for (OrderModel order : orders) {
                    Map<BookModel, Integer> bookQuantityMap = order.getBookQuantityMap();
                    for (Map.Entry<BookModel, Integer> entry : bookQuantityMap.entrySet()) {
                        BookModel book = entry.getKey();
                        if (!currentUser.get().hasOrdered(book)) {
                            recommendedBooks.add(book);
                        }
                    }
                }
            }
        }

        return new ArrayList<>(recommendedBooks);
    }

    private double calculateJaccardDistance(UserModel user1, UserModel user2) {
        List<BookModel> user1Books = user1.getAllPurchasedBooks();
        List<BookModel> user2Books = user2.getAllPurchasedBooks();

        Set<BookModel> union = new HashSet<>(user1Books);
        union.addAll(user2Books);

        Set<BookModel> intersection = new HashSet<>(user1Books);
        intersection.retainAll(user2Books);

        return 1.0 - ((double) intersection.size() / union.size());
    }

    private List<UserModel> getSimilarUsers(Map<UserModel, Double> jaccardDistances) {
        List<UserModel> similarUsers = new ArrayList<>();

        double minDistance = Double.MAX_VALUE;
        for (double distance : jaccardDistances.values()) {
            if (distance < minDistance) {
                minDistance = distance;
            }
        }

        for (Map.Entry<UserModel, Double> entry : jaccardDistances.entrySet()) {
            if (entry.getValue() == minDistance) {
                similarUsers.add(entry.getKey());
            }
        }

        return similarUsers;
    }
}
