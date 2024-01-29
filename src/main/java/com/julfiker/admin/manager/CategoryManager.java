package com.julfiker.admin.manager;

import com.julfiker.admin.dto.CategoryDto;


import java.util.List;

public interface CategoryManager {

    void saveCategory(CategoryDto categoryDto);
    CategoryDto findCategoryByID(Long ID);
    CategoryDto findCategoryByName(String name);
    List<CategoryDto> findAllCategories();

    void deleteCategoryByID(Long ID);

    void updateCategory(CategoryDto categoryDto);
}
