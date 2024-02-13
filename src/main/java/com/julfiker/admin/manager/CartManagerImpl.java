package com.julfiker.admin.manager;


import com.julfiker.admin.dto.CartDTO;
import com.julfiker.admin.dto.CartItemDTO;
import com.julfiker.admin.dto.ItemDTO;
import com.julfiker.admin.entity.Cart;
import com.julfiker.admin.entity.CartItem;
import com.julfiker.admin.entity.Customer;
import com.julfiker.admin.entity.Item;
import com.julfiker.admin.repository.CartItemRepository;
import com.julfiker.admin.repository.CartRepository;
import com.julfiker.admin.repository.CustomerRepository;
import com.julfiker.admin.repository.ItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class CartManagerImpl implements CartManager{

    @Autowired
    CartRepository cartRepository;
    @Autowired
    CartItemManager cartItemManager;
    @Autowired
    CustomerRepository customerRepository;
    @Autowired
    ItemRepository itemRepository;
    @Autowired
    CartItemRepository cartItemRepository;

    @Override
    public CartDTO convertToDTO(Cart cart){
        CartDTO cartDTO = new CartDTO();
        cartDTO.setCartID(cart.getCartID());
        cartDTO.setCreation_date(cart.getCreation_date());
        cartDTO.setCustomerID(cart.getCustomer().getCustomerID());
        cartDTO.setTotalPrice(cart.getTotalPrice());
        List<CartItem> cartItems = cart.getCartItems();
        List<Long> cartItemIDs = new ArrayList<>();
        for(CartItem cartItem : cartItems)
            cartItemIDs.add(cartItem.getCartItemID());
        cartDTO.setCartItemIDs(cartItemIDs);
        return cartDTO;
    }

    @Override
    public void addItemToCart(Long customerID, Long itemID, Integer quantity){
        Customer customer = customerRepository.findByCustomerID(customerID);
        if(customer == null){
            System.out.println("Could not find customer with the ID");
            return;
        }
        Cart cart = cartRepository.findCartByCustomer(customer);
        CartItemDTO cartItemDTO = new CartItemDTO();
        cartItemDTO.setItemID(itemID);
        cartItemDTO.setQuantity(quantity);
        if(cart == null) {
            cart = new Cart();
            cart.setCustomer(customer);
            customer.setCart(cart);
            BigDecimal itemPrice = itemRepository.findByItemID(itemID).getPrice();
            BigDecimal priceBeforeVAT = itemPrice.multiply(new BigDecimal(quantity));
            cart.setTotalPrice(priceBeforeVAT);

            cart.setCreation_date(LocalDateTime.now());
            cartRepository.save(cart);
            cart = cartRepository.findCartByCustomer(customer);
            cartItemDTO.setCartID(cart.getCartID());
            cartItemManager.saveCartItem(cartItemDTO);
            return;
        }
        BigDecimal totalOldPrice = cart.getTotalPrice();
        BigDecimal itemPrice = itemRepository.findByItemID(itemID).getPrice();
        BigDecimal newItemPrice =  itemPrice.multiply(new BigDecimal(quantity));
        BigDecimal newTotalPrice = totalOldPrice.add(newItemPrice);
        cartItemDTO.setCartID(cart.getCartID());
        cart.setTotalPrice(newTotalPrice);
        cartItemManager.saveCartItem(cartItemDTO);
    }

    @Override
    public List<CartDTO> findAll() {
        List<Cart> carts = cartRepository.findAll();
        List<CartDTO> cartDTOS = new ArrayList<>();
        for(Cart cart : carts)
            cartDTOS.add(convertToDTO(cart));
        return cartDTOS;
    }

    @Override
    public CartDTO findCartByCustomer(Long customerID) {
        return convertToDTO(cartRepository.findCartByCustomer(customerRepository.findByCustomerID(customerID)));
    }

    @Override
    public CartDTO findCartByID(Long ID) {
        return convertToDTO(cartRepository.findCartByCartID(ID));
    }

    @Override
    @Transactional
    public void deleteItemFromCart(Long customerID, Long cartItemID) {
        Customer customer = customerRepository.findByCustomerID(customerID);
        Cart cart = customer.getCart();
        if(cart == null || cart.getCartItems().isEmpty()){
            System.out.println("The cart is already empty.");
            return;
        }
        CartItem cartItem = cartItemRepository.findByCartItemID(cartItemID);
        if(cartItem == null){
            System.out.println("No cart item found with this ID");
            return;
        }
        if(!cart.getCartItems().contains(cartItem)){
            System.out.println("This cart item does not belong with this customer");
            return;
        }
        Item item = itemRepository.findByItemID(cartItem.getItem().getItemID());
        BigDecimal oldPrice = cart.getTotalPrice();
        BigDecimal itemPrice = item.getPrice();
        BigDecimal subPrice = itemPrice.multiply(new BigDecimal(cartItem.getQuantity()));
        BigDecimal newPrice = oldPrice.subtract(subPrice);
        cart.setTotalPrice(newPrice);
        boolean check = cart.getCartItems().remove(cartItem);
        if(!check)
            System.out.println("Remove failed");
        cartRepository.save(cart);
        item.getCartItems().remove(cartItem);
        itemRepository.save(item);
        cartItemRepository.deleteByCartItemID(cartItemID);
    }

    @Override
    public void emptyCart(Long customerID) {
        Cart cart = cartRepository.findCartByCustomer(customerRepository.findByCustomerID(customerID));

        List<CartItem> cartItems = new ArrayList<>(cart.getCartItems());

        for (CartItem cartItem : cartItems) {
            Item item = cartItem.getItem();

            item.getCartItems().remove(cartItem);
            itemRepository.save(item);

            cart.getCartItems().remove(cartItem);
            cartItemRepository.delete(cartItem);
        }


        cart.setTotalPrice(BigDecimal.ZERO);
        cartRepository.save(cart);
    }

    @Override
    @Transactional
    public void changeItemQuantity(Integer quantity, Long cartItemID) {
        CartItem cartItem = cartItemRepository.findByCartItemID(cartItemID);
        if(cartItem == null){
            System.out.println("No cart item found with the ID");
            return;
        }
        Cart cart = cartItem.getCart();
        if(quantity == 0){
            this.deleteItemFromCart(cart.getCustomer().getCustomerID(), cartItemID);
        }
        else{
            Integer oldQuantity = cartItem.getQuantity();
            BigDecimal oldPrice = cart.getTotalPrice();
            Item item = itemRepository.findByItemID(cartItem.getItem().getItemID());
            BigDecimal itemPrice = item.getPrice();
            BigDecimal subtractedPrice = oldPrice.subtract(itemPrice.multiply(new BigDecimal(oldQuantity)));
            BigDecimal newPrice = subtractedPrice.add(itemPrice.multiply(new BigDecimal(quantity)));
            cartItem.setQuantity(quantity);
            cart.setTotalPrice(newPrice);
        }
    }
}
