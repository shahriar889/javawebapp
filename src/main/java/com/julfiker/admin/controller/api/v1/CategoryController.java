package com.julfiker.admin.controller.api.v1;


import com.julfiker.admin.dto.CategoryDto;
import com.julfiker.admin.manager.CategoryManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1")
public class CategoryController {

    @Autowired
    CategoryManager categoryManager;

    @GetMapping("/categories")
    public List<CategoryDto> getAllCategories(@RequestParam(required = false) String name) {
        return categoryManager.findAllCategories().stream()
                .filter(attributesTypeDTO -> name == null || attributesTypeDTO.getName().equals(name))
                .collect(Collectors.toList());

    }

    @GetMapping("/categories/{ID}")
    public CategoryDto getCategoryByID(@PathVariable Long ID) {
        return categoryManager.findCategoryByID(ID);
    }

    @PostMapping("/categories")
    public void createCategory(@RequestBody CategoryDto categoryDto) {
        categoryManager.saveCategory(categoryDto);
    }

    @PutMapping("/categories/{ID}")
    public void updateCategory(@PathVariable Long ID, @RequestBody CategoryDto categoryDto) {
        categoryManager.updateCategory(categoryDto, ID);
    }

    @DeleteMapping("/categories/{ID}")
    @Transactional
    public void deleteCategory(@PathVariable Long ID) {
        categoryManager.deleteCategoryByID(ID);
    }
}
