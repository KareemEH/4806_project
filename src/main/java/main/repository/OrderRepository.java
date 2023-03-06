package main.repository;

import main.model.OrderModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<OrderModel, Long> {
    List<OrderModel> findByUser_Username(String username);
    List<OrderModel> findByUser_ShippingAddressContains(String keyword);
}