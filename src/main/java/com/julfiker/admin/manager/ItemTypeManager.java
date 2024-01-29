package com.julfiker.admin.manager;

import com.julfiker.admin.dto.ItemTypeDTO;

import java.util.List;

public interface ItemTypeManager {
    void saveItemType(ItemTypeDTO itemTypeDTO);
    void updateItemType(ItemTypeDTO itemTypeDTO);
    void deleteItemType(Long ID);

    List<ItemTypeDTO> findAllItemType();
    ItemTypeDTO findItemTypeByID(Long ID);
    ItemTypeDTO findItemTypeByName(String name);
}
