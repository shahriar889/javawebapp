package com.julfiker.admin.manager;

import com.julfiker.admin.dto.CategoryDto;
import com.julfiker.admin.entity.Category;

import java.util.List;

public interface CategoryManager {

    void saveCategory(CategoryDto categoryDto);
    Category findCategoryByID(Long ID);
    Category findCategoryByName(String name);
    List<Category> findAllCategories();

    void deleteCategoryByID(Long ID);

    void updateCategory(CategoryDto categoryDto);
}
