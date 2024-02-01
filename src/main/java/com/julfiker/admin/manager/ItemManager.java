package com.julfiker.admin.manager;

import com.julfiker.admin.dto.AttributeDTO;
import com.julfiker.admin.dto.CategoryDto;
import com.julfiker.admin.dto.ItemDTO;
import com.julfiker.admin.dto.MediaDTO;
import com.julfiker.admin.entity.Category;

import java.math.BigDecimal;
import java.util.List;
import java.util.Set;

public interface ItemManager {

    void saveItem(ItemDTO itemDTO);
    void updateItem(Long ID, ItemDTO itemDTO);
    void deleteItem(Long ID);

    List<ItemDTO> findAll();
    ItemDTO findItemByID(Long ID);

    Set<AttributeDTO> getAllItemAttributes(Long ID);

    Set<MediaDTO> getAllItemMedias(Long ID);

    void setRatingToItem(Long ID, BigDecimal rating);

    Set<CategoryDto> getAllItemCategories(Long ID);

    void addAttributeToItem(Long ID, Long ID2);

    void addMediaToItem(Long ID, Long ID2);
    void addCategoryToItem(Long ID, Long ID2);
}
