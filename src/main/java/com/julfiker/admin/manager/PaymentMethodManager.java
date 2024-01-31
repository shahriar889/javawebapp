package com.julfiker.admin.manager;

import com.julfiker.admin.dto.PaymentMethodDTO;
import com.julfiker.admin.entity.PaymentMethod;

import java.util.List;

public interface PaymentMethodManager {
    void savePaymentMethod(PaymentMethodDTO paymentMethodDTO);
    List<PaymentMethodDTO> findAll();
    PaymentMethodDTO findByID(Long ID);

    void updatePaymentMethod(PaymentMethodDTO paymentMethodDTO, Long ID);
    void deletePaymentMethod(Long ID);
}
