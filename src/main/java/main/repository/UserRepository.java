package main.repository;
import main.model.UserModel;
import org.springframework.stereotype.Repository;
import org.springframework.data.repository.CrudRepository;

@Repository
public interface UserRepository extends CrudRepository<UserModel, Integer> {
    UserModel findById(Long id);
    UserModel findByName(String username);
}