package com.julfiker.admin.api.v1;

import com.julfiker.admin.dto.PaymentMethodDTO;
import com.julfiker.admin.entity.PaymentMethod;
import com.julfiker.admin.manager.PaymentMethodManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/paymentMethod")
public class PaymentMethodController {

    @Autowired
    PaymentMethodManager paymentMethodManager;

    @GetMapping("/getAll")
    public List<PaymentMethodDTO> getAllPaymentMethod(){
        return paymentMethodManager.findAll();
    }
    @GetMapping("/getByID")
    public PaymentMethodDTO getPaymentMethodByID(@RequestParam Long ID){
        return paymentMethodManager.findByID(ID);
    }
    @GetMapping("/getByName")
    public PaymentMethodDTO getPaymentMethodByName(@RequestParam String name){
        return paymentMethodManager.findByName(name);
    }

    @PostMapping("/create")
    public void createPaymentMethod(@RequestBody PaymentMethodDTO paymentMethodDTO){
        paymentMethodManager.savePaymentMethod(paymentMethodDTO);
    }
    @PutMapping("/update")
    public void updatePaymentMethod(@RequestBody PaymentMethodDTO paymentMethodDTO){
        paymentMethodManager.updatePaymentMethod(paymentMethodDTO);
    }
    @DeleteMapping("/delete")
    public void deletePaymentMethod(@RequestParam Long ID){
        paymentMethodManager.deletePaymentMethod(ID);
    }
}
