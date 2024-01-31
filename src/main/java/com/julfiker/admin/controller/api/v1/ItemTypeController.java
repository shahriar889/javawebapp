package com.julfiker.admin.controller.api.v1;

import com.julfiker.admin.dto.ItemTypeDTO;
import com.julfiker.admin.manager.ItemTypeManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1")
public class ItemTypeController {

    @Autowired
    ItemTypeManager itemTypeManager;

    @GetMapping("/item-types")
    public List<ItemTypeDTO> getAllItemType(@RequestParam(required = false) String name) {
        return itemTypeManager.findAllItemType().stream()
                .filter(attributesTypeDTO -> name == null || attributesTypeDTO.getName().equals(name))
                .collect(Collectors.toList());
    }

    @GetMapping("/item-types/{ID}")
    public ItemTypeDTO getItemTypeByID(@PathVariable Long ID) {
        return itemTypeManager.findItemTypeByID(ID);
    }

    @PostMapping("/item-types")
    public void createItemType(@RequestBody ItemTypeDTO itemTypeDTO) {
        itemTypeManager.saveItemType(itemTypeDTO);
    }

    @PutMapping("/item-types/{ID}")
    public void updateItemType(@PathVariable Long ID, @RequestBody ItemTypeDTO itemTypeDTO) {
        itemTypeManager.updateItemType(itemTypeDTO, ID);
    }

    @DeleteMapping("/item-types{ID}")
    @Transactional
    public void deleteItemType(@PathVariable Long ID) {
        itemTypeManager.deleteItemType(ID);
    }
}
