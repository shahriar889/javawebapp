package com.julfiker.admin.controller.backOffice;

import com.julfiker.admin.dto.*;
import com.julfiker.admin.entity.Category;
import com.julfiker.admin.manager.*;
import com.julfiker.admin.repository.MediaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import net.coobird.thumbnailator.Thumbnails;


import javax.imageio.ImageIO;
import javax.validation.Valid;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.*;

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

    @GetMapping("items/new")
    public String showItemEntryForm(Model model) {
        List<CategoryDto> categories = categoryManager.findAllCategories();
        List<ItemTypeDTO> itemTypeDTOS = itemTypeManager.findAllItemType();
        List<SellerDTO> sellerDTOS = sellerManager.findAll();
        ItemDTO item = new ItemDTO();

        model.addAttribute("sellers", sellerDTOS);
        model.addAttribute("itemTypes", itemTypeDTOS);
        model.addAttribute("item", item);
        model.addAttribute("categories", categories);
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
            ItemDTO item = new ItemDTO();

            model.addAttribute("sellers", sellerDTOS);
            model.addAttribute("itemTypes", itemTypeDTOS);
            model.addAttribute("item", item);
            model.addAttribute("categories", categories);
            return "admin/item-entry-form";
        }


        Long savedMediaID = 0L;
        if (files != null && files.length > 0) {
            for (MultipartFile file : files) {
                if (file.isEmpty()) {
                    // Skip empty files
                    continue;
                }


                try {
                    // Save the original file
                    String originalFilename = StringUtils.cleanPath(file.getOriginalFilename());
                    String fileName = UUID.randomUUID().toString() + "_" + originalFilename;
                    File destDir = new File(uploadDir);
                    if (!destDir.exists()) {
                        destDir.mkdirs();
                    }
                    MediaDTO mediaDTO = new MediaDTO();
                    File destFile = new File(destDir.getAbsolutePath() + File.separator + fileName);
                    file.transferTo(destFile);
                    mediaDTO.setFileOriginalPath("assets/images/"+fileName);
                    mediaDTO.setFileSize((double) file.getSize());
                    int lastIndex = originalFilename.lastIndexOf('.');
                    if (lastIndex >= 0 && lastIndex < originalFilename.length() - 1) {
                        String fileExtension = originalFilename.substring(lastIndex);
                        mediaDTO.setFileExtension(fileExtension);
                        System.out.println("File extension: " + fileExtension);
                    } else {
                        System.out.println("No file extension found.");
                    }


                    // Generate thumbnail
                    BufferedImage originalImage = ImageIO.read(destFile);
                    int thumbnailWidth = 250; // Specify your desired width
                    int thumbnailHeight = 200; // Specify your desired height
                    BufferedImage thumbnail = Thumbnails.of(originalImage)
                            .size(thumbnailWidth, thumbnailHeight)
                            .asBufferedImage();

                    // Save the thumbnail
                    String thumbnailFileName = "thumbnail_" + fileName;
                    File thumbnailFile = new File(destDir.getAbsolutePath() + File.separator + thumbnailFileName);
                    ImageIO.write(thumbnail, "jpg", thumbnailFile);
                    mediaDTO.setFileThumbnailPath("assets/images/"+thumbnailFileName);
                    mediaManager.saveMedia(mediaDTO);
                    savedMediaID = mediaManager.getMediaID(mediaDTO.getFileOriginalPath());
                    System.out.println("Controller "+savedMediaID);


                    // Optionally, you can save the file paths to the database or do other processing
                } catch (IOException e) {
                    // Handle file processing errors
                    e.printStackTrace();
                }
            }
        }


        System.out.println(itemDTO.getName());
        System.out.println(itemDTO.getDescription());
        System.out.println(itemDTO.getStock_quantity());
        System.out.println(itemDTO.getSellerID());
        System.out.println(itemDTO.getItemTypeID());
        Set<Long> Ids = itemDTO.getCategoryIDs();
        if (Ids == null)
            System.out.println("null");
        else
            System.out.println("Size " + Ids.size());
        for (Long id : Ids)
            System.out.println("id" + id);
        System.out.println(itemDTO.getPrice());
        Set<Long> mediaIDs = itemDTO.getMediaIDs();
        if(mediaIDs == null)
            mediaIDs = new HashSet<>();
        if(savedMediaID != 0)
            mediaIDs.add(savedMediaID);
        itemDTO.setMediaIDs(mediaIDs);
        itemManager.saveItem(itemDTO);
        return "redirect:/items";
    }

    @GetMapping("/items")
    public String showAllItems(Model model){
        List<ItemDTO> itemDTOS = itemManager.findAll();

        List<MediaDTO> mediaDTOS = mediaManager.findAllMedia();
        model.addAttribute("items", itemDTOS);
        model.addAttribute("media", mediaDTOS);
        return "admin/item-list";
    }

    @GetMapping("items/edit/{ID}")
    public String editItem(@PathVariable Long ID, Model model){

        List<CategoryDto> categories = categoryManager.findAllCategories();
        List<ItemTypeDTO> itemTypeDTOS = itemTypeManager.findAllItemType();
        List<SellerDTO> sellerDTOS = sellerManager.findAll();

        model.addAttribute("sellers", sellerDTOS);
        model.addAttribute("itemTypes", itemTypeDTOS);
        model.addAttribute("categories", categories);
        ItemDTO itemDTO = itemManager.findItemByID(ID);
        model.addAttribute("item",itemDTO);
        return "admin/edit-item";
    }


    @DeleteMapping("/items/{ID}/delete")
    public String deleteItemByID(@PathVariable Long ID) {
        itemManager.deleteItem(ID);
        return "redirect:/items";
    }

    @GetMapping("items/{ID}/details")
    public String itemDetails(@PathVariable Long ID, Model model){
        System.out.println("in details");
        ItemDTO itemDTO = itemManager.findItemByID(ID);
        Set<MediaDTO> mediaDTOS = itemManager.getAllItemMedias(ID);
        List<ItemDTO> itemDTOS = itemManager.findAll();
        List<MediaDTO> mediaDTOList = mediaManager.findAllMedia();
        model.addAttribute("item",itemDTO);
//        model.addAttribute("media", mediaDTOS);
        model.addAttribute("items", itemDTOS);
        model.addAttribute("media", mediaDTOList);
        System.out.println(mediaDTOS.size());
        return "admin/item-details";
    }

}


