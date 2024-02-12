package com.julfiker.admin.controller.backOffice;

import com.julfiker.admin.dto.*;
import com.julfiker.admin.manager.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;

import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;


import javax.validation.Valid;
import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;


@Controller
public class ProductController {

    @Value("${upload.dir}")
    private String uploadDir;

    @Autowired
    CategoryManager categoryManager;
    @Autowired
    ItemTypeManager itemTypeManager;
    @Autowired
    SellerManager sellerManager;
    @Autowired
    ItemManager itemManager;
    @Autowired
    MediaManager mediaManager;
    @Autowired
    AttributeManager attributeManager;
    @Autowired
    ControllerHelpers helpers;

    @GetMapping("items/new")
    public String showItemEntryForm(Model model) {
        List<CategoryDto> categories = categoryManager.findAllCategories();
        List<ItemTypeDTO> itemTypeDTOS = itemTypeManager.findAllItemType();
        List<SellerDTO> sellerDTOS = sellerManager.findAll();
        List<AttributeDTO> attributeDTOS = attributeManager.findAllAttributes();
        ItemDTO item = new ItemDTO();

        model.addAttribute("sellers", sellerDTOS);
        model.addAttribute("itemTypes", itemTypeDTOS);
        model.addAttribute("item", item);
        model.addAttribute("categories", categories);
        model.addAttribute("attributes", attributeDTOS);
        return "admin/item-entry-form";
    }

    @PostMapping("/items/save")
    public String saveItem(@Valid @ModelAttribute("item") ItemDTO itemDTO,
                           BindingResult result,
                           Model model,
                           @RequestParam(value = "file", required = false) MultipartFile[] files) {

        if (result.hasErrors()) {
            System.out.println(result.getAllErrors());
            List<CategoryDto> categories = categoryManager.findAllCategories();
            List<ItemTypeDTO> itemTypeDTOS = itemTypeManager.findAllItemType();
            List<SellerDTO> sellerDTOS = sellerManager.findAll();
            List<AttributeDTO> attributeDTOS = attributeManager.findAllAttributes();
            ItemDTO item = new ItemDTO();

            model.addAttribute("sellers", sellerDTOS);
            model.addAttribute("itemTypes", itemTypeDTOS);
            model.addAttribute("item", item);
            model.addAttribute("categories", categories);
            model.addAttribute("attributes", attributeDTOS);
            return "admin/item-entry-form";
        }
        Long savedMediaID = helpers.saveImages(files);
        Set<Long> mediaIDs = itemDTO.getMediaIDs();
        if (mediaIDs == null)
            mediaIDs = new HashSet<>();
        if (savedMediaID != 0)
            mediaIDs.add(savedMediaID);
        itemDTO.setMediaIDs(mediaIDs);
        itemManager.saveItem(itemDTO);
        return "redirect:/items";
    }

    @GetMapping("/items")
    public String showAllItems(Model model, @RequestParam(required = false) String sortBy, @RequestParam(required = false) String searchName) {
        List<ItemDTO> itemDTOS = itemManager.findAll();

        // Filter items by name if searchName is provided
        if (searchName != null && !searchName.isEmpty()) {
            System.out.println("Search");
            itemDTOS = itemDTOS.stream()
                    .filter(item -> item.getName().toLowerCase().contains(searchName.toLowerCase()))
                    .collect(Collectors.toList());
        }

        // Sort the list of items based on the sortBy parameter
        if ("priceLow".equals(sortBy)) {
            itemDTOS.sort(Comparator.comparing(ItemDTO::getPrice));
        } else if ("priceHigh".equals(sortBy)) {
            itemDTOS.sort(Comparator.comparing(ItemDTO::getPrice).reversed());
        } else if ("ratingHigh".equals(sortBy)) {
            itemDTOS.sort(Comparator.comparing(ItemDTO::getRating).reversed());
        } else if ("stockLow".equals(sortBy)) {
            itemDTOS.sort(Comparator.comparing(ItemDTO::getStock_quantity));
        }

        List<MediaDTO> mediaDTOS = mediaManager.findAllMedia();
        model.addAttribute("items", itemDTOS);
        model.addAttribute("media", mediaDTOS);
        return "admin/item-list";
    }


    @GetMapping("items/edit/{ID}")
    public String editItem(@PathVariable Long ID, Model model) {

        List<CategoryDto> categories = categoryManager.findAllCategories();
        List<ItemTypeDTO> itemTypeDTOS = itemTypeManager.findAllItemType();
        List<SellerDTO> sellerDTOS = sellerManager.findAll();

        model.addAttribute("sellers", sellerDTOS);
        model.addAttribute("itemTypes", itemTypeDTOS);
        model.addAttribute("categories", categories);
        ItemDTO itemDTO = itemManager.findItemByID(ID);
        model.addAttribute("item", itemDTO);
        List<AttributeDTO> attributeDTOS = attributeManager.findAllAttributes();
        model.addAttribute("attributes", attributeDTOS);
        return "admin/edit-item";
    }


    @DeleteMapping("/items/{ID}")
    @Transactional
    public String deleteItemByID(@PathVariable Long ID) {
        itemManager.deleteItem(ID);
        return "redirect:/items";
    }

    @GetMapping("items/{ID}/details")
    public String itemDetails(@PathVariable Long ID, Model model) {
        System.out.println("in details");
        ItemDTO itemDTO = itemManager.findItemByID(ID);
        Set<MediaDTO> mediaDTOS = itemManager.getAllItemMedias(ID);
        SellerDTO sellerDTO = itemManager.getSeller(ID);
        Set<CategoryDto> categoryDTOs = itemManager.getAllItemCategories(ID);
        List<ItemTypeDTO> itemTypeDTOS = itemTypeManager.findAllItemType();
        model.addAttribute("item", itemDTO);
        model.addAttribute("medias", mediaDTOS);
        model.addAttribute("seller", sellerDTO);
        model.addAttribute("categories", categoryDTOs);
        model.addAttribute("itemTypes", itemTypeDTOS);
        System.out.println(mediaDTOS.size());
        return "admin/item-details";
    }

    @GetMapping("/items/{ID}/rating/{num}")
    public String addRatings(@PathVariable Long ID, @PathVariable BigDecimal num) {
        itemManager.setItemRating(ID, num);
        return "redirect:/items";
    }

}


