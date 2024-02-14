package com.julfiker.admin.manager;

import com.julfiker.admin.dto.OrderDTO;
import com.julfiker.admin.entity.*;
import com.julfiker.admin.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class OrderManagerImpl implements OrderManager{

    @Autowired
    OrderRepository orderRepository;
    @Autowired
    OrderItemRepository orderItemRepository;
    @Autowired
    CartRepository cartRepository;
    @Autowired
    ShippingMethodRepository shippingMethodRepository;
    @Autowired
    CustomerRepository customerRepository;

    @Override
    public OrderDTO convertToDto(Order order) {
        OrderDTO orderDTO = new OrderDTO();
        orderDTO.setOrderID(order.getOrderID());
        orderDTO.setCreation_date(order.getCreation_date());
        orderDTO.setCustomerID(order.getCustomer().getCustomerID());
        orderDTO.setDeliverDate(order.getDeliveryDate());
        orderDTO.setTotalPrice(order.getTotalPrice());
        orderDTO.setStatus(order.isStatus());
        orderDTO.setInvoiceID(order.getInvoiceID());
        orderDTO.setShippingMethodID(order.getShippingMethod().getShippingMethodID());
        List<Long> orderItemIDs = new ArrayList<>();
        for(OrderItem orderItem : order.getOrderItems())
            orderItemIDs.add(orderItem.getOrderItemID());
        orderDTO.setOrderItemIDs(orderItemIDs);
        if(order.getLast_updated() != null)
            orderDTO.setLast_updated(order.getLast_updated());
        return orderDTO;
    }

    @Override
    public void confirmOrder(Long cartID, Long shippingMethodID, String invoiceID) {
        Cart cart = cartRepository.findCartByCartID(cartID);
        ShippingMethod shippingMethod = shippingMethodRepository.findByShippingMethodID(shippingMethodID);
        if(cart == null || shippingMethod == null){
            System.out.println("Could not find shipping method or cart");
            return;
        }
        List<CartItem> cartItems = cart.getCartItems();
        if(cartItems.isEmpty()){
            System.out.println("Cart is empty nothing to order");
            return;
        }
        Order order = new Order();
        order.setShippingMethod(shippingMethod);
        List<Order> orders = shippingMethod.getOrders();
        if(orders == null)
            orders = new ArrayList<>();
        shippingMethod.setLast_updated(LocalDateTime.now());
        orders.add(order);
        shippingMethod.setOrders(orders);
        shippingMethodRepository.save(shippingMethod);
        Customer customer = cart.getCustomer();
        order.setCustomer(customer);
        List<Order> orderList = customer.getOrders();
        if(orderList == null)
            orderList = new ArrayList<>();
        orderList.add(order);
        customer.setOrders(orderList);
        customerRepository.save(customer);
        order.setStatus(true);
        order.setInvoiceID(invoiceID);
        order.setDeliveryDate(LocalDateTime.now().plusDays(shippingMethod.getEstimatedDeliveryTime()+shippingMethod.getHandlingTime()));
        order.setCreation_date(LocalDateTime.now());
        BigDecimal price = cart.getTotalPrice();
        price = price.add(price.multiply(new BigDecimal("0.15")));
        order.setTotalPrice(price);
        List<OrderItem> orderItems = new ArrayList<>();
        for(CartItem cartItem : cartItems){
            OrderItem orderItem = new OrderItem();
            orderItem.setOrder(order);
            orderItem.setQuantity(cartItem.getQuantity());
            orderItem.setItem(cartItem.getItem());
            orderItem.setCreation_date(LocalDateTime.now());
            orderItems.add(orderItem);
            orderItemRepository.save(orderItem);
        }
        order.setOrderItems(orderItems);
        orderRepository.save(order);
    }

    @Override
    public List<OrderDTO> getAllOrder() {
        List<Order> orders = orderRepository.findAll();
        List<OrderDTO> orderDTOS = new ArrayList<>();
        for(Order order : orders)
            orderDTOS.add(convertToDto(order));
        return orderDTOS;
    }

    @Override
    public List<OrderDTO> getAllOrderByCustomer(Long ID) {
        List<Order> orders = orderRepository.findAllByCustomer_CustomerID(ID);
        List<OrderDTO> orderDTOS = new ArrayList<>();
        for(Order order : orders)
            orderDTOS.add(convertToDto(order));
        return orderDTOS;
    }

    @Override
    public List<OrderDTO> getAllOrderByShippingMethod(Long ID) {
        List<Order> orders = orderRepository.findAllByShippingMethod_ShippingMethodID(ID);
        List<OrderDTO> orderDTOS = new ArrayList<>();
        for(Order order : orders)
            orderDTOS.add(convertToDto(order));
        return orderDTOS;
    }

    @Override
    public List<OrderDTO> getAllOrderByStatus(boolean status) {
        List<Order> orders = orderRepository.findAllByStatus(status);
        List<OrderDTO> orderDTOS = new ArrayList<>();
        for(Order order : orders)
            orderDTOS.add(convertToDto(order));
        return orderDTOS;
    }

    @Override
    public OrderDTO getOrderByOrderID(Long ID) {
        Order order = orderRepository.findByOrderID(ID);
        if(order == null){
            System.out.println("Could not find any order with the ID");
            return new OrderDTO();
        }
        return convertToDto(order);
    }

    @Override
    @Transactional
    public void deleteOrderByID(Long ID) {
        Order order = orderRepository.findByOrderID(ID);
        if(order == null){
            System.out.println("Could not find any order with the ID");
            return;
        }
        List<OrderItem> orderItems = order.getOrderItems();
        if(orderItems == null || orderItems.isEmpty()){
            System.out.println("Order is empty");
            return;
        }
        for(OrderItem orderItem : orderItems){
            order.getOrderItems().remove(orderItem);
            orderItemRepository.delete(orderItem);
        }
        orderRepository.deleteByOrderID(ID);
    }

    @Override
    public void changeOrderStatus(Long ID) {
        Order order = orderRepository.findByOrderID(ID);
        if(order == null){
            System.out.println("Could not find any order with the ID");
            return;
        }
        order.setStatus(!order.isStatus());
    }
}
