package com.julfiker.admin.manager;

import com.julfiker.admin.dto.ShippingMethodDTO;
import com.julfiker.admin.entity.ShippingMethod;

import java.util.List;

public interface ShippingMethodManager {

    ShippingMethodDTO convertToDTO(ShippingMethod shippingMethod);
    void saveShippingMethod(ShippingMethodDTO DTO);
    List<ShippingMethodDTO> findAll();
    ShippingMethodDTO findByID(Long ID);
    List<ShippingMethodDTO> findAllByInternational(boolean check);
    void deleteShippingMethodByID(Long ID);

}
