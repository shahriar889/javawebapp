package com.julfiker.admin.controller.api.v1;

import com.julfiker.admin.dto.PaymentMethodDTO;
import com.julfiker.admin.entity.PaymentMethod;
import com.julfiker.admin.manager.PaymentMethodManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1")
public class PaymentMethodController {

    @Autowired
    PaymentMethodManager paymentMethodManager;

    @GetMapping("/payment-methods")
    public List<PaymentMethodDTO> getAllPaymentMethod(@RequestParam(required = false) String name) {
        return paymentMethodManager.findAll().stream()
                .filter(attributesTypeDTO -> name == null || attributesTypeDTO.getName().equals(name))
                .collect(Collectors.toList());
    }

    @GetMapping("/payment-methods/{ID}")
    public PaymentMethodDTO getPaymentMethodByID(@PathVariable Long ID) {
        return paymentMethodManager.findByID(ID);
    }

    @PostMapping("/payment-methods")
    public void createPaymentMethod(@RequestBody PaymentMethodDTO paymentMethodDTO) {
        paymentMethodManager.savePaymentMethod(paymentMethodDTO);
    }

    @PutMapping("/payment-methods/{ID}")
    public void updatePaymentMethod(@PathVariable Long ID, @RequestBody PaymentMethodDTO paymentMethodDTO) {
        paymentMethodManager.updatePaymentMethod(paymentMethodDTO, ID);
    }

    @DeleteMapping("/payment-methods/{ID}")
    @Transactional
    public void deletePaymentMethod(@PathVariable Long ID) {
        paymentMethodManager.deletePaymentMethod(ID);
    }
}
