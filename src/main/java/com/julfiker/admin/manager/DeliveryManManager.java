package com.julfiker.admin.manager;

import com.julfiker.admin.dto.DeliveryManDTO;
import com.julfiker.admin.dto.UserDto;
import com.julfiker.admin.entity.DeliveryMan;

import java.math.BigDecimal;
import java.util.List;

public interface DeliveryManManager {

    DeliveryManDTO convertToDTO(DeliveryMan deliveryMan);
    List<DeliveryManDTO> findAll();
    List<DeliveryManDTO> findByAvailable(boolean check);
    DeliveryManDTO findByID(Long ID);
    DeliveryManDTO findByLicensePlate(String msg);
    void saveDeliveryMan(DeliveryManDTO deliveryManDTO, UserDto userDto);
    void deleteDeliveryManByID(Long ID);
    void changeAvailable(Long ID);
    void addRating(Long ID, BigDecimal rating);
    void confirmDelivery(Long manID);

}