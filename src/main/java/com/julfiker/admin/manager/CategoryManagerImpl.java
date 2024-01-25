package com.julfiker.admin.manager;

import com.julfiker.admin.dto.CategoryDto;
import com.julfiker.admin.entity.Category;
import com.julfiker.admin.entity.Item;
import com.julfiker.admin.repository.CategoryRepository;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.security.PublicKey;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;


@Service
public class CategoryManagerImpl implements CategoryManager{

    @Autowired
    private CategoryRepository categoryRepository;

    private void convertToCategory(CategoryDto categoryDto, Category category){
        category.setName(categoryDto.getName());
        category.setDescription(categoryDto.getDescription());
        if(categoryDto.getParentID() != null){
            Long parentID_cur = categoryDto.getParentID();
            category.setParentCategory(categoryRepository.findByCategoryID(parentID_cur));
        }
        if(categoryDto.getPerishable() != null){
            category.setPerishable(categoryDto.getPerishable());
        }

    }

    @Override
    public void saveCategory(CategoryDto categoryDto){
        Category category = new Category();
        this.convertToCategory(categoryDto, category);
        List<Item> to_insert = new ArrayList<>();
        category.setItems(to_insert);
        if(category.getCreation_date() == null){
            category.setCreation_date(LocalDateTime.now());
        }
        categoryRepository.save(category);
    }

    @Override
    public void updateCategory(CategoryDto categoryDto){
        if(categoryDto.getCategoryID() == null){
            System.out.println("Cannot Update without ID");
            return;
        }
        Category category = categoryRepository.findByCategoryID(categoryDto.getCategoryID());
        if(category == null){
            System.out.println("Could not find any Category associated with the provided ID");
            return;
        }

        this.convertToCategory(categoryDto,category);
        category.setLast_updated(LocalDateTime.now());
        categoryRepository.save(category);

    }

    @Override
    public Category findCategoryByID(Long ID) {
        return categoryRepository.findByCategoryID(ID);
    }
    @Override
    public List<Category> findAllCategories(){
        return categoryRepository.findAll();
    }
    @Override
    public Category findCategoryByName(String name){
        return categoryRepository.findByName(name);
    }

    @Override
    public void deleteCategoryByID(Long ID){
        categoryRepository.deleteByCategoryID(ID);
    }
}
