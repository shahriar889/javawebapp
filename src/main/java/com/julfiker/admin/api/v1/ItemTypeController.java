package com.julfiker.admin.api.v1;

import com.julfiker.admin.dto.ItemTypeDTO;
import com.julfiker.admin.manager.ItemTypeManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/itemType")
public class ItemTypeController {

    @Autowired
    ItemTypeManager itemTypeManager;

    @GetMapping("/getAll")
    public List<ItemTypeDTO> getAllItemType(){
        return itemTypeManager.findAllItemType();
    }

    @GetMapping("/getByID")
    public ItemTypeDTO getItemTypeByID(@RequestParam Long ID){
        return itemTypeManager.findItemTypeByID(ID);
    }

    @GetMapping("/getByName")
    public ItemTypeDTO getItemTypeByName(@RequestParam String name){
        return itemTypeManager.findItemTypeByName(name);
    }

    @PostMapping("/create")
    public void createItemType(@RequestBody ItemTypeDTO itemTypeDTO){
        itemTypeManager.saveItemType(itemTypeDTO);
    }

    @PutMapping("/update")
    public void updateItemType(@RequestBody ItemTypeDTO itemTypeDTO){
        itemTypeManager.updateItemType(itemTypeDTO);
    }

    @DeleteMapping("/delete")
    @Transactional
    public void deleteItemType(@RequestParam Long ID){
        itemTypeManager.deleteItemType(ID);
    }
}
