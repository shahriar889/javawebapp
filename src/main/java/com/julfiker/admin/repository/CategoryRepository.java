package com.julfiker.admin.repository;

import com.julfiker.admin.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {
    List<Category> findAll();
    Category findByCategoryID(Long categoryID);

    Category findByName(String name);

    void deleteByCategoryID(Long categoryID);
}
