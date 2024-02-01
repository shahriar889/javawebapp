package com.julfiker.admin.repository;

import com.julfiker.admin.entity.Customer;
import com.julfiker.admin.entity.PaymentInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PaymentInfoRepository extends JpaRepository<PaymentInfo, Long> {

    PaymentInfo findByPaymentInfoID(Long ID);
    PaymentInfo findByCardType(String cardType);
    void deleteByPaymentInfoID(Long ID);
    List<PaymentInfo> findAllByCustomer(Customer customer);

}
