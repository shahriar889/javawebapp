package com.julfiker.admin.manager;

import com.julfiker.admin.dto.CartDTO;
import com.julfiker.admin.entity.Cart;

import java.util.List;

public interface CartManager {

    CartDTO convertToDTO(Cart cart);
    void addItemToCart(Long customerID, Long itemID, Integer quantity);
    void deleteItemFromCart(Long customerID, Long cartItemID);

    CartDTO findCartByCustomer(Long customerID);
    CartDTO findCartByID(Long ID);
    List<CartDTO> findAll();
}
