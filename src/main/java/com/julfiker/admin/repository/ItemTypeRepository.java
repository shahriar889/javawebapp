package com.julfiker.admin.repository;

import com.julfiker.admin.entity.ItemType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ItemTypeRepository extends JpaRepository<ItemType, Long> {
    List<ItemType> findAll();
    ItemType findByItemTypeID(Long ID);
    void deleteByItemTypeID(Long ID);
}
