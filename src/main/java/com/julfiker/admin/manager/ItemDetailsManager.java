package com.julfiker.admin.manager;

import com.julfiker.admin.dto.ItemDetailsDTO;
import com.julfiker.admin.entity.ItemDetails;

import java.util.List;

public interface ItemDetailsManager {

    void saveItemDetails(ItemDetailsDTO detailsDTO);

    void saveMultipleItemDetails(List<ItemDetailsDTO> detailsDTOS);

    ItemDetailsDTO convertToDTO(ItemDetails details);

    List<ItemDetailsDTO> findAllByItemID(Long ID);

    List<ItemDetailsDTO> findAll();

    ItemDetailsDTO findByID(Long ID);

    void deleteItemDetails(Long ID);

    void deleteAllItemsDetailsByItemID(Long ID);

    void updateItemDetails(Long ID, ItemDetailsDTO detailsDTO);

}