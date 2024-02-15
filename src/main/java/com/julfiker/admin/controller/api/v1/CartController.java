package com.julfiker.admin.controller.api.v1;

import com.julfiker.admin.dto.CartDTO;
import com.julfiker.admin.dto.CartItemDTO;
import com.julfiker.admin.dto.CustomerDTO;
import com.julfiker.admin.manager.CartItemManager;
import com.julfiker.admin.manager.CartManager;
import com.julfiker.admin.manager.CustomerManager;
import com.julfiker.admin.manager.ItemManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/v1")
public class CartController {

    @Autowired
    CartItemManager cartItemManager;
    @Autowired
    CartManager cartManager;
    @Autowired
    CustomerManager customerManager;
    @Autowired
    ItemManager itemManager;

    @PostMapping("/carts/customers/{customerID}/items/{itemID}")
    public void addItemToCustomerCart(@PathVariable Long customerID, @PathVariable Long itemID, @RequestBody Integer quantity){
        cartManager.addItemToCart(customerID,itemID,quantity);
    }

    @GetMapping("/carts/customers/{ID}")
    public CartDTO getCustomerCart(@PathVariable Long ID){
        return cartManager.findCartByCustomer(ID);
    }
    @GetMapping("/carts/cart-items/{cartItemID}")
    public CartItemDTO getSingleCartItem( @PathVariable Long cartItemID){
        return cartItemManager.findByCartItemByID(cartItemID);
    }

    @DeleteMapping("/carts/customers/{customerID}")
    @Transactional
    public void deleteWholeCart(@PathVariable Long customerID){
        cartManager.emptyCart(customerID);

    }

    @DeleteMapping("/carts/customers/{customerID}/cart-items/{cartItemID}")
    @Transactional
    public void deleteSingleCartItem(@PathVariable Long customerID, @PathVariable Long cartItemID){
        cartManager.deleteItemFromCart(customerID,cartItemID);
    }

    @PutMapping("/carts/cart-items/{cartItemID}/quantity/{num}")
    public void changeItemQuantity(@PathVariable Long cartItemID, @PathVariable Integer num){
        cartManager.changeItemQuantity(num, cartItemID);
    }
}
