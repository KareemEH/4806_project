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

    //Stub
    @Override
    public boolean addtoCart(long userid, long bookid, int quantity){
        return true;
    }

}
