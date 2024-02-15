package com.julfiker.admin.controller.api.v1;

import com.julfiker.admin.dto.OrderDTO;
import com.julfiker.admin.manager.OrderManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1")
public class OrderController {
    @Autowired
    OrderManager orderManager;
    @PostMapping("/orders/carts/{cartID}/shipping-methods/{methodId}/{invoiceID}")
    public void confirmOrder(@PathVariable Long cartID,  @PathVariable Long methodId, @PathVariable String invoiceID){
        orderManager.confirmOrder(cartID,methodId, invoiceID);
    }

    @GetMapping("/orders")
    public List<OrderDTO> getAll(){
        return orderManager.getAllOrder();
    }

    @GetMapping("/orders/customers/{customerID}")
    public List<OrderDTO> getAllByCustomer(@PathVariable Long customerID){
        return orderManager.getAllOrderByCustomer(customerID);
    }

    @GetMapping("/orders/{orderID}")
    public OrderDTO getOrderByID(@PathVariable Long orderID){
        return orderManager.getOrderByOrderID(orderID);
    }

    @GetMapping("/orders/status/{status}")
    public List<OrderDTO> getAllByStatus(@PathVariable boolean status){
        return orderManager.getAllOrderByStatus(status);
    }

    @PutMapping("/orders/{orderID}/status")
    public void changeOrderStatus(@PathVariable Long orderID){
        orderManager.changeOrderStatus(orderID);
    }

    @DeleteMapping("orders/{orderID}")
    @Transactional
    public void deleteByID(@PathVariable Long orderID){
        orderManager.deleteOrderByID(orderID);
    }

    @PutMapping("/orders/{orderID}/delivery-man/{manID}")
    public void assignDeliveryMan(@PathVariable Long orderID, @PathVariable Long manID){
        orderManager.assignDeliveryMan(orderID, manID);
    }
}
