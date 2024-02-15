package com.julfiker.admin.controller.api.v1;

import com.julfiker.admin.dto.ShippingMethodDTO;
import com.julfiker.admin.manager.ShippingMethodManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1")
public class ShippingMethodController {

    @Autowired
    ShippingMethodManager methodManager;

    @PostMapping("/shipping-methods")
    public void saveShippingMethod(@RequestBody ShippingMethodDTO methodDTO){
        methodManager.saveShippingMethod(methodDTO);
    }

    @GetMapping("/shipping-methods")
    public List<ShippingMethodDTO> getAll(){
        return methodManager.findAll();
    }

    @GetMapping("/shipping-methods/{ID}")
    public ShippingMethodDTO getByID(@PathVariable Long ID){
        return methodManager.findByID(ID);
    }
    @GetMapping("/shipping-methods/is-international/{check}")
    public List<ShippingMethodDTO> getAllByInternational(@PathVariable boolean check){
        return methodManager.findAllByInternational(check);
    }

    @DeleteMapping("/shipping-methods/{ID}")
    @Transactional
    public void deleteByID(@PathVariable Long ID){
        methodManager.deleteShippingMethodByID(ID);
    }
}
