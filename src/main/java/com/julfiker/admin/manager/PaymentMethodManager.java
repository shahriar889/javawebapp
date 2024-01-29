package com.julfiker.admin.manager;

import com.julfiker.admin.dto.PaymentMethodDTO;
import com.julfiker.admin.entity.PaymentMethod;

import java.util.List;

public interface PaymentMethodManager {
    void savePaymentMethod(PaymentMethodDTO paymentMethodDTO);
    List<PaymentMethodDTO> findAll();
    PaymentMethodDTO findByID(Long ID);
    PaymentMethodDTO findByName(String name);
    void updatePaymentMethod(PaymentMethodDTO paymentMethodDTO);
    void deletePaymentMethod(Long ID);
}
