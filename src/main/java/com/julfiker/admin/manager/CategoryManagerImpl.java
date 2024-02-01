package com.julfiker.admin.manager;

import com.julfiker.admin.dto.CategoryDto;
import com.julfiker.admin.entity.Category;
import com.julfiker.admin.entity.Item;
import com.julfiker.admin.repository.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;


@Service
public class CategoryManagerImpl implements CategoryManager{

    @Autowired
    private CategoryRepository categoryRepository;

    @Override
    public CategoryDto convertToDTO(Category category){
        CategoryDto categoryDto = new CategoryDto();
        categoryDto.setCategoryID(category.getCategoryID());
        categoryDto.setPerishable(category.getPerishable());
        categoryDto.setName(category.getName());
        categoryDto.setDescription(category.getDescription());
        Category parentCategory = category.getParentCategory();
        if (parentCategory != null) {
            categoryDto.setParentID(parentCategory.getCategoryID());
        }
        categoryDto.setCreation_date(category.getCreation_date());
        if(category.getLast_updated() != null)
            categoryDto.setLast_updated(category.getLast_updated());
        return categoryDto;
    }


    @Override
    public void saveCategory(CategoryDto categoryDto){
        Category category = new Category();
        if(categoryDto.getName() == null || categoryDto.getDescription() == null){
            System.out.println("While creating name or description cannot be empty");
            return;
        }
        category.setName(categoryDto.getName());
        category.setDescription(categoryDto.getDescription());
        if(categoryDto.getParentID() != null){
            Long parentID_cur = categoryDto.getParentID();
            category.setParentCategory(categoryRepository.findByCategoryID(parentID_cur));
        }
        if(categoryDto.getPerishable() != null){
            category.setPerishable(categoryDto.getPerishable());
        }
        List<Item> to_insert = new ArrayList<>();
        category.setItems(to_insert);
        if(category.getCreation_date() == null){
            category.setCreation_date(LocalDateTime.now());
        }
        categoryRepository.save(category);
    }

    @Override
    public void updateCategory(CategoryDto categoryDto, Long ID){
        Category category = categoryRepository.findByCategoryID(ID);
        if(category == null){
            System.out.println("Could not find any Category associated with the provided ID");
            return;
        }
        if(categoryDto.getName() != null)
            category.setName(categoryDto.getName());
        if(categoryDto.getDescription() != null)
            category.setDescription(categoryDto.getDescription());
        if(categoryDto.getParentID() != null){

            Category parent = categoryRepository.findByCategoryID(categoryDto.getParentID());
            if(parent != null)
                category.setParentCategory(parent);
        }
        category.setLast_updated(LocalDateTime.now());
        categoryRepository.save(category);

    }

    @Override
    public CategoryDto findCategoryByID(Long ID) {
        Category category = categoryRepository.findByCategoryID(ID);
        if(category == null){
            System.out.println("Could find category with this ID");
            return new CategoryDto();
        }
        return convertToDTO(category);
    }
    @Override
    public List<CategoryDto> findAllCategories(){
        List<Category> categoryList = categoryRepository.findAll();
        List<CategoryDto> categoryDtoList = new ArrayList<>();
        for(Category c: categoryList){
            CategoryDto categoryDto = convertToDTO(c);
            categoryDtoList.add(categoryDto);
        }
        return categoryDtoList;
    }


    @Override
    public void deleteCategoryByID(Long ID){
        categoryRepository.deleteByCategoryID(ID);
    }
}
