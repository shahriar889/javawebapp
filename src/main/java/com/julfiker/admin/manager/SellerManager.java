package com.julfiker.admin.manager;

import com.julfiker.admin.dto.SellerDTO;
import com.julfiker.admin.dto.UserDto;
import com.julfiker.admin.entity.Seller;

import java.math.BigDecimal;
import java.util.List;

public interface SellerManager {

    void saveSeller(SellerDTO sellerDTO, UserDto userDto);
    void updateSeller(SellerDTO sellerDTO, Long ID);
    void deleteSellerByID(Long ID);

    SellerDTO findBySellerID(Long ID);
    List<SellerDTO> findAll();

    void addMediaToSeller(Long sellerID, Long mediaID);

    void setSellerRating(Long ID, BigDecimal rating);

    void addAssociationWithPaymentMethod(Long sellerID, Long pmID);

    SellerDTO convertToDTO(Seller seller);
}
