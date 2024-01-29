package com.julfiker.admin.manager;

import com.julfiker.admin.dto.ItemTypeDTO;
import com.julfiker.admin.entity.Item;
import com.julfiker.admin.entity.ItemType;
import com.julfiker.admin.repository.ItemTypeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class ItemTypeManagerImpl implements ItemTypeManager{

    @Autowired
    private ItemTypeRepository itemTypeRepository;

    private ItemTypeDTO convertToDTO(ItemType itemType){
        ItemTypeDTO itemTypeDTO = new ItemTypeDTO();
        itemTypeDTO.setItemTypeID(itemType.getItemTypeID());
        itemTypeDTO.setName(itemType.getName());
        itemTypeDTO.setDescription(itemType.getDescription());
        itemTypeDTO.setCreation_date(itemType.getCreation_date());
        if(itemType.getLast_updated() != null)
            itemTypeDTO.setLast_updated(itemType.getLast_updated());
        List<Item> items = itemType.getItems();
        List<Long> itemIDList = new ArrayList<>();
        for(Item item:items)
            itemIDList.add(item.getItemID());
        itemTypeDTO.setItemIDList(itemIDList);
        return itemTypeDTO;
    }

    @Override
    public void saveItemType(ItemTypeDTO itemTypeDTO){
        ItemType itemType = new ItemType();
        if(itemTypeDTO.getName() == null || itemTypeDTO.getDescription() == null){
            System.out.println("Need name and description to create new item type.");
            return;
        }
        itemType.setName(itemTypeDTO.getName());
        itemType.setDescription(itemTypeDTO.getDescription());
        itemType.setCreation_date(LocalDateTime.now());
        List<Item> items = new ArrayList<>();
        itemType.setItems(items);
        itemTypeRepository.save(itemType);
    }

    @Override
    public void updateItemType(ItemTypeDTO itemTypeDTO){
        if(itemTypeDTO.getItemTypeID() == null){
            System.out.println("Cannot update without ID");
            return;
        }
        ItemType itemType = itemTypeRepository.findByItemTypeID(itemTypeDTO.getItemTypeID());
        if(itemType == null){
            System.out.println("Could not find item type with this ID");
            return;
        }
        if(itemTypeDTO.getName() != null)
            itemType.setName(itemTypeDTO.getName());
        if(itemTypeDTO.getDescription() != null)
            itemType.setDescription(itemTypeDTO.getDescription());
        itemType.setLast_updated(LocalDateTime.now());
        itemTypeRepository.save(itemType);
    }

    @Override
    public void deleteItemType(Long ID){
        itemTypeRepository.deleteByItemTypeID(ID);
    }

    @Override
    public List<ItemTypeDTO> findAllItemType(){
        List<ItemType> itemTypes = itemTypeRepository.findAll();
        List<ItemTypeDTO> itemTypeDTOS = new ArrayList<>();
        for(ItemType itemType : itemTypes)
            itemTypeDTOS.add(convertToDTO(itemType));
        return itemTypeDTOS;
    }

    @Override
    public ItemTypeDTO findItemTypeByID(Long ID){
        ItemType itemType = itemTypeRepository.findByItemTypeID(ID);
        if(itemType == null){
            System.out.println("Could not Find item type with this ID");
            return new ItemTypeDTO();
        }
        return convertToDTO(itemType);
    }

    @Override
    public ItemTypeDTO findItemTypeByName(String name){
        ItemType itemType = itemTypeRepository.findByName(name);
        if(itemType == null){
            System.out.println("Could not Find item type with this name");
            return new ItemTypeDTO();
        }
        return convertToDTO(itemType);
    }
}
