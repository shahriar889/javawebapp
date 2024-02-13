package com.julfiker.admin.manager;

import com.julfiker.admin.dto.CartDTO;
import com.julfiker.admin.dto.CartItemDTO;
import com.julfiker.admin.entity.CartItem;

import java.util.List;

public interface CartItemManager {

    void saveCartItem(CartItemDTO cartItemDTO);
    void updateCartItem(Long ID, CartItemDTO cartItemDTO);

    List<CartItemDTO> findAllCartItems();
    CartItemDTO findByCartItemByID(Long ID);
    CartItemDTO convertToDTO(CartItem cartItem);

    List<CartItemDTO> getAllCartItemsByCart(Long cartID);

}
