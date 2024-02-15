package com.julfiker.admin.manager;

import com.julfiker.admin.dto.OrderDTO;
import com.julfiker.admin.entity.Order;

import java.util.List;

public interface OrderManager {
    OrderDTO convertToDto(Order order);
    void confirmOrder(Long cartID, Long shippingMethodID, String invoiceID);
    List<OrderDTO> getAllOrder();
    List<OrderDTO> getAllOrderByCustomer(Long ID);
    OrderDTO getOrderByOrderID(Long ID);
    List<OrderDTO> getAllOrderByShippingMethod(Long ID);
    List<OrderDTO> getAllOrderByStatus(boolean status);
    void changeOrderStatus(Long ID);
    void deleteOrderByID(Long ID);
    void assignDeliveryMan(Long orderID, Long manID);


}
