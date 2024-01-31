package com.julfiker.admin.repository;

import com.julfiker.admin.entity.Attribute;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AttributeRepository extends JpaRepository<Attribute, Long> {
    List<Attribute> findAll();
    Attribute findByAttributeID(Long ID);

    void deleteByAttributeID(Long ID);
}
