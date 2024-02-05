package com.julfiker.admin.manager;

import com.julfiker.admin.dto.CartDTO;
import com.julfiker.admin.dto.CartItemDTO;
import com.julfiker.admin.entity.Cart;
import com.julfiker.admin.entity.CartItem;
import com.julfiker.admin.entity.Item;
import com.julfiker.admin.manager.CartItemManager;
import com.julfiker.admin.repository.CartItemRepository;
import com.julfiker.admin.repository.CartRepository;
import com.julfiker.admin.repository.ItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class CartItemManagerImpl implements CartItemManager {

    @Autowired
    CartItemRepository cartItemRepository;

    @Autowired
    ItemRepository itemRepository;

    @Autowired
    CartRepository cartRepository;

    @Override
    public CartItemDTO convertToDTO(CartItem cartItem){
        CartItemDTO cartItemDTO = new CartItemDTO();
        cartItemDTO.setCartItemID(cartItem.getCartItemID());
        cartItemDTO.setItemID(cartItem.getCartItemID());
        cartItemDTO.setCartID(cartItemDTO.getCartID());
        cartItemDTO.setQuantity(cartItem.getQuantity());
        cartItemDTO.setCreation_date(cartItem.getCreation_date());
        if(cartItem.getLast_updated() != null)
            cartItemDTO.setLast_updated(cartItem.getLast_updated());
        return cartItemDTO;
    }

    @Override
    public void saveCartItem(CartItemDTO cartItemDTO){
        if(cartItemDTO.getQuantity() == null|| cartItemDTO.getItemID() == null || cartItemDTO.getCartID() == null){
            System.out.println("Not enough info to make cart item");
            return;
        }
        Item item = itemRepository.findByItemID(cartItemDTO.getItemID());
        CartItem cartItem = new CartItem();
        cartItem.setItem(item);
        cartItem.setQuantity(cartItem.getQuantity());
        Cart cart = cartRepository.findCartByCartID(cartItemDTO.getCartID());
        cartItem.setCart(cart);
        cart.getCartItems().add(cartItem);
        cartItem.setCreation_date(LocalDateTime.now());
        item.getCartItems().add(cartItem);
        cartRepository.save(cart);
        itemRepository.save(item);
        cartItemRepository.save(cartItem);




    }

    @Override
    public void updateCartItem(Long ID, CartItemDTO cartItemDTO){
        CartItem cartItem = cartItemRepository.findByCartItemID(ID);
        if(cartItem == null){
            System.out.println("Could not find cartItem with this ID");
            return;
        }
        if(cartItemDTO.getQuantity() != null)
            cartItem.setQuantity(cartItemDTO.getQuantity());
    }
    @Override
    @Transactional
    public void deleteCartItemByID(Long ID){
        cartItemRepository.deleteByCartItemID(ID);
    }

    @Override
    public List<CartItemDTO> findAllCartItems(String name){
        List<CartItemDTO> cartItemDTOS = new ArrayList<>();
        List<CartItem> cartItems = cartItemRepository.findAll();
        for(CartItem cartItem : cartItems)
            cartItemDTOS.add(convertToDTO(cartItem));
        return cartItemDTOS;
    }
    @Override
    public CartItemDTO findByCartItemByID(Long ID){
       CartItem cartItem = cartItemRepository.findByCartItemID(ID);
       if(cartItem == null){
           System.out.println("Could not find Cart Item this ID");
           return new CartItemDTO();
       }
       return convertToDTO(cartItem);
    }

}
