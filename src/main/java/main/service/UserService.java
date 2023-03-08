package main.service;

public interface UserService {

    void createUser(String username,String password);
    boolean verifyLogin(String username, String password);
}
