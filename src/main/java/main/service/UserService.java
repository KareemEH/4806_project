package main.service;

public interface UserService {

    void createUser(String username,String password) throws Exception;
    boolean verifyLogin(String username, String password);
}
