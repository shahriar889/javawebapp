package com.julfiker.admin.manager;

import com.julfiker.admin.dto.CartItemDTO;

import java.util.List;

public interface CartItemManager {

    void saveCartItem(CartItemDTO cartItemDTO);
    void updateCartItem(CartItemDTO cartItemDTO);
    void deleteCartItemByID(Long ID);
    List<CartItemDTO> findAllCartItems(String name);
    CartItemDTO findCartItemByID(Long ID);

    void addItemToCart(Long ID, Long ID2);
}
