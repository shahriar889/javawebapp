package com.julfiker.admin.controller.api.v1;

import com.julfiker.admin.dto.AttributeDTO;
import com.julfiker.admin.dto.CategoryDto;
import com.julfiker.admin.dto.ItemDTO;
import com.julfiker.admin.dto.MediaDTO;
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
}
