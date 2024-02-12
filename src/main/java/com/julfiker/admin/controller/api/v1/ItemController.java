package com.julfiker.admin.controller.api.v1;

import com.julfiker.admin.dto.*;
import com.julfiker.admin.entity.Item;
import com.julfiker.admin.entity.ItemDetails;
import com.julfiker.admin.manager.ItemDetailsManager;
import com.julfiker.admin.manager.ItemManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.parameters.P;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1")
public class ItemController {

    @Autowired
    ItemManager itemManager;
    @Autowired
    ItemDetailsManager itemDetailsManager;

    @PostMapping("/items")
    public void createItem(@RequestBody ItemDTO itemDTO) {
        itemManager.saveItem(itemDTO);
    }

    @GetMapping("/items")
    public List<ItemDTO> getItems(@RequestParam(required = false) String name, @RequestParam(required = false) BigDecimal price,
                                  @RequestParam(required = false) BigDecimal rating) {
        return itemManager.findAll().stream()
                .filter(itemDTO -> (name == null || itemDTO.getName().equals(name)) &&
                        (price == null || itemDTO.getPrice().compareTo(price) <= 0) &&
                        (rating == null || itemDTO.getRating().compareTo(rating) >= 0))
                .collect(Collectors.toList());
    }

    @GetMapping("/items/{ID}")
    public ItemDTO getItemByID(@PathVariable Long ID) {
        return itemManager.findItemByID(ID);
    }

    @PutMapping("/items/{ID}")
    public void updateItem(@PathVariable Long ID, @RequestBody ItemDTO itemDTO) {
        itemManager.updateItem(ID, itemDTO);
    }

    @DeleteMapping("/items/{ID}")
    @Transactional
    public void deleteItem(@PathVariable Long ID) {
        itemManager.deleteItem(ID);
    }

    @PutMapping("/items/{ID}/ratings")
    public void setRatingToItem(@PathVariable Long ID, @RequestBody BigDecimal rating) {
        itemManager.setRatingToItem(ID, rating);
    }

    @GetMapping("/items/{ID}/attributes")
    public Set<AttributeDTO> getAllItemAttributes(@PathVariable Long ID) {
        return itemManager.getAllItemAttributes(ID);
    }

    @GetMapping("/items/{ID}/medias")
    public Set<MediaDTO> getAllItemMedias(@PathVariable Long ID){
        return itemManager.getAllItemMedias(ID);
    }
    @GetMapping("/items/{ID}/categories")
    public Set<CategoryDto> getAllItemCategories(@PathVariable Long ID){
        return itemManager.getAllItemCategories(ID);
    }

    @PutMapping("/items/{ID}/attributes/{ID2}")
    public void setAttributeToItem(@PathVariable Long ID, @PathVariable Long ID2){
        itemManager.addAttributeToItem(ID, ID2);
    }

    @PutMapping("/items/{ID}/medias/{ID2}")
    public void setMediaToItem(@PathVariable Long ID, @PathVariable Long ID2){
        itemManager.addMediaToItem(ID, ID2);
    }

    @PutMapping("/items/{ID}/categories/{ID2}")
    public void setCategoryToItem(@PathVariable Long ID, @PathVariable Long ID2){
        itemManager.addCategoryToItem(ID, ID2);
    }

    @PostMapping("items/{ID}/item-details")
    public void saveItemDetailsWIthItem(@PathVariable Long ID, @RequestBody List<ItemDetailsDTO> detailsDTOS){
        for(ItemDetailsDTO dto : detailsDTOS)
            dto.setItemID(ID);
        itemDetailsManager.saveMultipleItemDetails(detailsDTOS);
    }

    @GetMapping("items/{ID}/item-details")
    public List<ItemDetailsDTO> getAllItemDetailsByID(@PathVariable Long ID){
        return itemDetailsManager.findAllByItemID(ID);
    }

    @PutMapping("items/{ID}/item-details")
    public void updateItemDetails(@PathVariable Long ID, @RequestBody ItemDetailsDTO detailsDTO){
        itemDetailsManager.updateItemDetails(ID, detailsDTO);
    }

    @DeleteMapping("items/{ID}/item-details/{ID2}")
    @Transactional
    public void detailSingleItemDetails(@PathVariable Long ID, @PathVariable Long ID2){
        ItemDetailsDTO detailsDTO = itemDetailsManager.findByID(ID2);
        if(detailsDTO.getItemID().equals(ID))
            itemDetailsManager.deleteItemDetails(ID2);
        else
            System.out.println("Cannot delete this detail does not belong with this item");
    }

    @DeleteMapping("items/{ID}/item-details")
    @Transactional
    public void detailAllItemDetailsFromItem(@PathVariable Long ID){

        if(itemManager.findItemByID(ID).getItemDetailsIDs().isEmpty()){
            System.out.println("The item details list is already empty");
            return;
        }
        itemDetailsManager.deleteAllItemsDetailsByItemID(ID);
    }
}
