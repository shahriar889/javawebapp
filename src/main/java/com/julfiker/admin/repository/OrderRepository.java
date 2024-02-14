package com.julfiker.admin.repository;

import com.julfiker.admin.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<Order,Long> {

    List<Order> findAll();
    List<Order> findAllByStatus(boolean status);
    List<Order> findAllByShippingMethod_ShippingMethodID(Long ID);
    Order findByOrderID(Long ID);
    Order findByInvoiceID(String invoiceID);
    void deleteByOrderID(Long ID);
    List<Order> findAllByCustomer_CustomerID(Long ID);
}
