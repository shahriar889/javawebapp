package com.julfiker.admin.repository;

import com.julfiker.admin.entity.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Long> {
    List<Customer> findAll();
    Customer findByCustomerID(Long ID);


    void deleteCustomerByCustomerID(Long ID);
}
