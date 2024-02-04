package com.julfiker.admin.repository;

import com.julfiker.admin.entity.Cart;
import com.julfiker.admin.entity.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CartRepository extends JpaRepository<Cart, Long> {

    Cart findCartByCartID(Long ID);
    List<Cart> findAll();
    Cart findCartByCustomer(Customer customer);

    void deleteCartByCartID(Long ID);

}
