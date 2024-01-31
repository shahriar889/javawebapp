package com.julfiker.admin.repository;

import com.julfiker.admin.entity.PaymentMethod;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PaymentMethodRepository extends JpaRepository<PaymentMethod, Long> {
    List<PaymentMethod> findAll();

    PaymentMethod getPaymentMethodByPaymentMethodID(Long ID);

    void deleteByPaymentMethodID(Long ID);

}
