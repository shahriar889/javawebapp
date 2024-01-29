package com.julfiker.admin.manager;

import com.julfiker.admin.dto.SellerDTO;
import com.julfiker.admin.entity.Seller;

import java.math.BigDecimal;
import java.util.List;

public interface SellerManager {

    void saveSeller(SellerDTO sellerDTO);
    void updateSeller(SellerDTO sellerDTO);
    void deleteSellerByID(Long ID);

    SellerDTO findBySellerID(Long ID);
    SellerDTO findByName(String name);
    SellerDTO findByAddress(String address);
    SellerDTO findBySocialMedia(String socialMedia);
    List<SellerDTO> findAllByRating(BigDecimal rating);
    List<SellerDTO> findAllByReturnPolicy(String returnPolicy);
    List<SellerDTO> findAll();

    void setSellerRating(Long ID, BigDecimal rating);

    void addAssociationWithPaymentMethod(Long sellerID, Long pmID);
}
