package main.service;

import main.model.UserModel;

public interface UserService {

    UserModel createUser(String username,String password) throws Exception;
    boolean verifyLogin(String username, String password);
}
