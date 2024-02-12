package com.julfiker.admin.repository;

import com.julfiker.admin.entity.ItemDetails;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ItemDetailsRepository extends JpaRepository<ItemDetails, Long> {

    List<ItemDetails> findAllByItem_ItemID(Long ID);
    List<ItemDetails> findAll();

    ItemDetails findByItemDetailsID(Long ID);

    void deleteByItemDetailsID(Long ID);

    void deleteAllByItem_ItemID(Long ID);
}
