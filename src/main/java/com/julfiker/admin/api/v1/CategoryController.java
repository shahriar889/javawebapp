package com.julfiker.admin.api.v1;


import com.julfiker.admin.dto.CategoryDto;
import com.julfiker.admin.entity.Category;
import com.julfiker.admin.manager.CategoryManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/category")
public class CategoryController {

    @Autowired
    CategoryManager categoryManager;

    @GetMapping("/getAll")
    public List<CategoryDto> getAllCategories(){
        return categoryManager.findAllCategories();
    }
    @GetMapping("/getByID")
    public CategoryDto getCategoryByID(@RequestParam Long ID){
        return categoryManager.findCategoryByID(ID);
    }

    @GetMapping("/getByName")
    public CategoryDto getCategoryByID(@RequestParam String name){
        return categoryManager.findCategoryByName(name);
    }

    @PostMapping("/create")
    public void createCategory(@RequestBody CategoryDto categoryDto){
        categoryManager.saveCategory(categoryDto);
    }

    @PutMapping("/update")
    public void updateCategory(CategoryDto categoryDto){
        categoryManager.updateCategory(categoryDto);
    }
    @DeleteMapping("/delete")
    @Transactional
    public void deleteCategory(@RequestParam Long ID){
        categoryManager.deleteCategoryByID(ID);
    }
}
