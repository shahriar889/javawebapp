package com.julfiker.admin.repository;

import com.julfiker.admin.entity.Category;
import com.julfiker.admin.entity.Item;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ItemRepository extends JpaRepository<Item, Long> {

    List<Item> findAll();
    Item findByItemID(Long ID);



}
