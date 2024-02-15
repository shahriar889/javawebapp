package com.julfiker.admin.controller.api.v1;

import com.julfiker.admin.dto.DeliveryManDTO;
import com.julfiker.admin.dto.DeliveryManUserWrapperDTO;
import com.julfiker.admin.manager.DeliveryManManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

@RestController
@RequestMapping("/api/v1")
public class DeliveryManController {

    @Autowired
    DeliveryManManager manManager;

    @PostMapping("/delivery-men")
    public void saveDeliveryMan(@RequestBody DeliveryManUserWrapperDTO wrapperDTO){
        manManager.saveDeliveryMan(wrapperDTO.getManDTO(), wrapperDTO.getUserDto());
    }

    @GetMapping("/delivery-men")
    public List<DeliveryManDTO> getAll(){
        return manManager.findAll();
    }

    @GetMapping("/delivery-men/{manID}")
    public DeliveryManDTO  getByID(@PathVariable Long manID){
       return manManager.findByID(manID);
    }

    @GetMapping("/delivery-men/available/{check}")
    public  List<DeliveryManDTO> getAllByAvailable(@PathVariable boolean check){
        return manManager.findByAvailable(check);
    }

    @GetMapping("/delivery-men/license-plates/{plate}")
    public DeliveryManDTO getByLicensePlate(@PathVariable String plate){
        return manManager.findByLicensePlate(plate);
    }

    @PutMapping("/delivery-men/{manID}/ratings")
    public void addRating(@PathVariable Long manID, @RequestBody BigDecimal rating){
        manManager.addRating(manID, rating);
    }

    @PutMapping("/delivery-men/{manID}/available")
    public void changeAvailable(@PathVariable Long manID){
        manManager.changeAvailable(manID);
    }

    @DeleteMapping("delivery-men/{manID}")
    @Transactional
    public void deleteDeliveryMan(@PathVariable Long manID){
        manManager.deleteDeliveryManByID(manID);
    }

    @PutMapping("/delivery-man/{manID}/confirm-order")
    public void confirmOrder(@PathVariable Long manID){
        manManager.confirmDelivery(manID);
    }
}
