package com.julfiker.admin.manager;

import com.julfiker.admin.dto.OrderItemDTO;
import com.julfiker.admin.entity.OrderItem;

import java.util.List;

public interface OrderItemManager {

    void saveOrderItem(OrderItemDTO orderItemDTO);
    OrderItemDTO convertToDTO(OrderItem orderItem);

    OrderItemDTO findByOrderItemID(Long ID);
    List<OrderItemDTO> finAll();
    void deleteOrderItemByID(Long ID);

    List<OrderItemDTO> findAllByOrderID(Long ID);
}
