package com.julfiker.admin.manager;

import com.julfiker.admin.dto.OrderItemDTO;
import com.julfiker.admin.entity.OrderItem;
import com.julfiker.admin.repository.ItemRepository;
import com.julfiker.admin.repository.OrderItemRepository;
import com.julfiker.admin.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class OrderItemManagerImpl implements  OrderItemManager{

    @Autowired
    OrderItemRepository orderItemRepository;
    @Autowired
    ItemRepository itemRepository;
    @Autowired
    OrderRepository orderRepository;

    @Override
    public OrderItemDTO convertToDTO(OrderItem orderItem) {
        OrderItemDTO DTO = new OrderItemDTO();
        DTO.setItemID(orderItem.getItem().getItemID());
        DTO.setOrderID(orderItem.getOrder().getOrderID());
        DTO.setOrderItemID(orderItem.getOrderItemID());
        DTO.setCreation_date(orderItem.getCreation_date());
        if(orderItem.getLast_updated() != null)
            DTO.setLast_updated(orderItem.getLast_updated());
        DTO.setQuantity(orderItem.getQuantity());
        return DTO;
    }

    @Override
    public void saveOrderItem(OrderItemDTO orderItemDTO) {
        if(orderItemDTO.getOrderID() == null || orderItemDTO.getItemID() == null ||
        orderItemDTO.getQuantity() == null){
            System.out.println("Not enough info to make Order item");
        }
        OrderItem orderItem = orderItemRepository.findByOrderItemID(orderItemDTO.getOrderItemID());
        if(orderItem == null){
            orderItem = new OrderItem();
            orderItem.setCreation_date(LocalDateTime.now());
        }
        else
            orderItem.setLast_updated(LocalDateTime.now());
        orderItem.setQuantity(orderItemDTO.getQuantity());
        orderItem.setItem(itemRepository.findByItemID(orderItemDTO.getOrderItemID()));
        orderItem.setOrder(orderRepository.findByOrderID(orderItemDTO.getOrderID()));
        orderItemRepository.save(orderItem);
    }

    @Override
    public List<OrderItemDTO> finAll() {
        List<OrderItem> orderItems = orderItemRepository.findAll();
        List<OrderItemDTO> orderItemDTOS = new ArrayList<>();
        for(OrderItem orderItem : orderItems)
            orderItemDTOS.add(convertToDTO(orderItem));
        return orderItemDTOS;
    }

    @Override
    public OrderItemDTO findByOrderItemID(Long ID) {
        OrderItem orderItem = orderItemRepository.findByOrderItemID(ID);
        if(orderItem == null) {
            System.out.println("Could not find any order item with the ID");
            return new OrderItemDTO();
        }
        return convertToDTO(orderItem);
    }

    @Override
    @Transactional
    public void deleteOrderItemByID(Long ID) {
        orderItemRepository.deleteByOrderItemID(ID);
    }

    @Override
    public List<OrderItemDTO> findAllByOrderID(Long ID) {
        List<OrderItem> orderItems = orderItemRepository.findAllByOrder_OrderID(ID);
        List<OrderItemDTO> orderItemDTOS = new ArrayList<>();
        for(OrderItem orderItem : orderItems)
            orderItemDTOS.add(convertToDTO(orderItem));
        return orderItemDTOS;
    }
}

