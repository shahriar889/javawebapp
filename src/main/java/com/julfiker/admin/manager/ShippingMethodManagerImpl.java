package com.julfiker.admin.manager;

import com.julfiker.admin.dto.ShippingMethodDTO;
import com.julfiker.admin.entity.Order;
import com.julfiker.admin.entity.ShippingMethod;
import com.julfiker.admin.repository.DeliveryManRepository;
import com.julfiker.admin.repository.OrderRepository;
import com.julfiker.admin.repository.ShippingMethodRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class ShippingMethodManagerImpl implements ShippingMethodManager {

    @Autowired
    ShippingMethodRepository shippingMethodRepository;
    @Autowired
    DeliveryManRepository deliveryManRepository;
    @Autowired
    OrderRepository orderRepository;

    @Override
    public ShippingMethodDTO convertToDTO(ShippingMethod shippingMethod) {
        ShippingMethodDTO dto = new ShippingMethodDTO();
        dto.setAvailable(shippingMethod.isAvailable());
        dto.setCost(shippingMethod.getCost());
        dto.setDescription(shippingMethod.getDescription());
        dto.setName(shippingMethod.getName());
        dto.setCreation_date(shippingMethod.getCreation_date());
        dto.setShippingMethodID(shippingMethod.getShippingMethodID());
        dto.setTrackingURL(shippingMethod.getTrackingURL());
        dto.setHandlingTime(shippingMethod.getHandlingTime());
        dto.setEstimatedDeliveryTime(shippingMethod.getEstimatedDeliveryTime());
        dto.setInternational(shippingMethod.isInternational());
        dto.setDeliveryManID(shippingMethod.getDeliveryMan().getDeliveryManID());
        List<Long> orderIDs = new ArrayList<>();
        for (Order order : shippingMethod.getOrders())
            orderIDs.add(order.getOrderID());
        dto.setOrderID(orderIDs);
        if (shippingMethod.getLast_updated() != null)
            dto.setLast_updated(shippingMethod.getLast_updated());
        return dto;
    }

    @Override
    public void saveShippingMethod(ShippingMethodDTO DTO) {
        if (DTO.getCost() == null || DTO.getDescription() == null || DTO.getName() == null
                || DTO.getEstimatedDeliveryTime() == null || DTO.getHandlingTime() == null
                || DTO.getTrackingURL() == null) {
            System.out.println("Not enough info to make Shipping Method");
            return;
        }
        ShippingMethod shippingMethod = shippingMethodRepository.findByShippingMethodID(DTO.getShippingMethodID());
        if (shippingMethod == null) {
            shippingMethod = new ShippingMethod();
            shippingMethod.setCreation_date(LocalDateTime.now());
        } else
            shippingMethod.setLast_updated(LocalDateTime.now());
        shippingMethod.setCost(DTO.getCost());
        shippingMethod.setAvailable(DTO.isAvailable());
        List<Long> oderIds = DTO.getOrderID();
        List<Order> orders = new ArrayList<>();
        for(Long ID : oderIds)
            orders.add(orderRepository.findByOrderID(ID));
        shippingMethod.setOrders(orders);
        shippingMethod.setInternational(DTO.isInternational());
        shippingMethod.setTrackingURL(DTO.getTrackingURL());
        shippingMethod.setName(DTO.getName());
        shippingMethod.setDescription(DTO.getDescription());
        shippingMethod.setHandlingTime(DTO.getHandlingTime());
        shippingMethod.setEstimatedDeliveryTime(DTO.getEstimatedDeliveryTime());
        if(DTO.getDeliveryManID() != null)
            shippingMethod.setDeliveryMan(deliveryManRepository.findByDeliveryManID(DTO.getDeliveryManID()));
    }

    @Override
    public List<ShippingMethodDTO> findAll() {
        List<ShippingMethodDTO> list = new ArrayList<>();
        for(ShippingMethod method : shippingMethodRepository.findAll())
            list.add(convertToDTO(method));
        return list;
    }

    @Override
    public List<ShippingMethodDTO> findAllByInternational(boolean check) {
        List<ShippingMethodDTO> list = new ArrayList<>();
        for(ShippingMethod method : shippingMethodRepository.findAllByInternational(check))
            list.add(convertToDTO(method));
        return list;
    }

    @Override
    public ShippingMethodDTO findByID(Long ID) {
        ShippingMethod shippingMethod = shippingMethodRepository.findByShippingMethodID(ID);
        if(shippingMethod == null){
            System.out.println("Could not find shipping method with the ID");
            return new ShippingMethodDTO();
        }
        return convertToDTO(shippingMethod);
    }

    @Override
    public ShippingMethodDTO findByDeliveryMan(Long ID) {
        ShippingMethod shippingMethod = shippingMethodRepository.findByDeliveryMan_DeliveryManID(ID);
        if(shippingMethod == null){
            System.out.println("Could not find delivery man with the ID");
            return new ShippingMethodDTO();
        }
        return convertToDTO(shippingMethod);
    }

    @Override
    public void addShippingMethodToOrder(Long orderID, Long methodID) {
        Order order = orderRepository.findByOrderID(orderID);
        ShippingMethod shippingMethod = shippingMethodRepository.findByShippingMethodID(methodID);
        if( order == null || shippingMethod == null){
            System.out.println("Could not find shipping method or order");
            return;
        }
        order.setShippingMethod(shippingMethod);
        shippingMethod.getOrders().add(order);
        orderRepository.save(order);
        shippingMethodRepository.save(shippingMethod);
    }

    @Override
    @Transactional
    public void deleteShippingMethodByID(Long ID) {
        shippingMethodRepository.deleteByShippingMethodID(ID);
    }
}
