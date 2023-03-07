package main.service;

import main.model.UserModel;
import main.repository.UserRepository;
import org.springframework.stereotype.Service;


@Service
public class UserServiceImpl implements UserService{

    private final UserRepository userRepo;

    public UserServiceImpl(UserRepository userRepo){
        this.userRepo = userRepo;
    }

    @Override
    public void createUser(String username, String password) {
        UserModel userModel = new UserModel(username, password);
        userRepo.save(userModel);
    }

    @Override
    public boolean verifyLogin(String username, String password){
        UserModel userModel = userRepo.findByUsername(username);
        return(userModel.getPassword().equals(password));
    }

}
