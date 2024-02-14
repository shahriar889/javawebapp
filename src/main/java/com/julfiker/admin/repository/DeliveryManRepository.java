package com.julfiker.admin.repository;

import com.julfiker.admin.entity.DeliveryMan;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DeliveryManRepository extends JpaRepository<DeliveryMan, Long> {
    List<DeliveryMan> findAll();
    DeliveryMan findByDeliveryManID(Long ID);
    DeliveryMan findByLicensePlate(String msg);
    List<DeliveryMan> findByAvailable(boolean check);
}
