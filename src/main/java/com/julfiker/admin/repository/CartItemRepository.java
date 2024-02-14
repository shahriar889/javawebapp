package com.julfiker.admin.repository;

import com.julfiker.admin.entity.CartItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CartItemRepository extends JpaRepository<CartItem,Long> {
    CartItem findByCartItemID(Long ID);
    List<CartItem> findAll();
    List<CartItem> findAllByCart_CartID(Long ID);
    void deleteByCartItemID(Long ID);
}
