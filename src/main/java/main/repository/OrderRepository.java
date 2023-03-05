package main.repository;

import main.model.OrderModel;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderRepository extends CrudRepository<OrderModel, Integer> {
    List<OrderModel> findByUser_Username(String username);
    List<OrderModel> findByUser_Id(Integer userId);
    List<OrderModel> findByUser_ShippingAddressContains(String keyword);
}