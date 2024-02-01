package com.julfiker.admin.manager;

import com.julfiker.admin.dto.CategoryDto;
import com.julfiker.admin.entity.Category;


import java.util.List;

public interface CategoryManager {

    void saveCategory(CategoryDto categoryDto);
    CategoryDto findCategoryByID(Long ID);

    List<CategoryDto> findAllCategories();

    void deleteCategoryByID(Long ID);

    void updateCategory(CategoryDto categoryDto, Long ID);

    CategoryDto convertToDTO(Category category);
}
