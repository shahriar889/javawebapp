package com.julfiker.admin.repository;

import com.julfiker.admin.entity.Attributes_Type;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface Attributes_TypeRepository extends JpaRepository<Attributes_Type, Long> {
    List<Attributes_Type> findAll();
    Attributes_Type findByAttributesTypeID(Long ID);
    Attributes_Type findByName(String name);

    void deleteByAttributesTypeID(Long ID);
}
