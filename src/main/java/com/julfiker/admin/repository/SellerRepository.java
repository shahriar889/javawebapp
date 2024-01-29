package com.julfiker.admin.repository;

import com.julfiker.admin.entity.Seller;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;

@Repository
public interface SellerRepository extends JpaRepository<Seller,Long> {

    List<Seller> findAll();
    Seller findBySellerID(Long ID);
    Seller findByName(String name);
    Seller findByAddress(String address);
    Seller findBySocialMedia(String socialMedia);
    List<Seller> findAllByRating(BigDecimal rating);
    List<Seller> findAllByReturnPolicy(String returnPolicy);
    int deleteBySellerID(Long ID);
}
