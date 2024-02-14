package com.julfiker.admin.repository;

import com.julfiker.admin.entity.ShippingMethod;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ShippingMethodRepository extends JpaRepository<ShippingMethod, Long> {

    List<ShippingMethod> findAll();
    ShippingMethod findByDeliveryMan_DeliveryManID(Long ID);
    List<ShippingMethod> findAllByInternational(boolean check);
    ShippingMethod findByShippingMethodID(Long ID);
    void deleteByShippingMethodID(Long ID);
}
