package main.repository;
import main.model.UserModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<UserModel, Long> {
    UserModel findByUsername(String username);

    @Query("SELECT DISTINCT u FROM UserModel u JOIN FETCH u.orderList")
    List<UserModel> findAllWithOrders();
}