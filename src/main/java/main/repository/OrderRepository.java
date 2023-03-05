package main.repository;

import main.model.OrderModel;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface OrderRepository extends CrudRepository<OrderModel, Integer> {
    List<OrderModel> findByUser_Username(String username);
    List<OrderModel> findByUser_Id(Integer userId);
    List<OrderModel> findByUser_ShippingAddressContains(String keyword);
}