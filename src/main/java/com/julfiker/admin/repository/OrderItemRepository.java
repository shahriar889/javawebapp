package com.julfiker.admin.repository;

import com.julfiker.admin.entity.OrderItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderItemRepository extends JpaRepository<OrderItem, Long> {

    List<OrderItem> findAll();
    List<OrderItem> findAllByOrder_OrderID(Long ID);
    OrderItem findByOrderItemID(Long ID);

    void deleteByOrderItemID(Long ID);
}
