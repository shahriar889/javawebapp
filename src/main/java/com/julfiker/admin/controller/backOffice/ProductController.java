package com.julfiker.admin.controller.backOffice;

import com.julfiker.admin.dto.CategoryDto;
import com.julfiker.admin.dto.ItemDTO;
import com.julfiker.admin.dto.UserDto;
import com.julfiker.admin.entity.Category;
import com.julfiker.admin.manager.CategoryManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import javax.validation.Valid;
import java.util.List;

@Controller
public class ProductController {

    @Autowired
    CategoryManager categoryManager;

    @GetMapping("items/new")
    public String showItemEntryForm(Model model){
        List<CategoryDto> categories = categoryManager.findAllCategories();
        ItemDTO item = new ItemDTO();
        model.addAttribute("item", item);
        model.addAttribute("categories", categories);
        return "admin/item-entry-form";
    }

    @PostMapping("/items/save")
    public String saveItem (@Valid @ModelAttribute("item") ItemDTO itemDTO,
                          BindingResult result,
                          Model model) {
        return "admin/index";
    }
}
