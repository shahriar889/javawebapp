package com.julfiker.admin.manager;

import com.julfiker.admin.dto.AttributeDTO;
import com.julfiker.admin.dto.Attributes_TypeDTO;

import java.util.List;

public interface AttributeManager {

    void saveAttribute(AttributeDTO attributeDTO, Attributes_TypeDTO attrTypesDTO);
    void updateAttribute(AttributeDTO attributeDTO, Long ID);
    void deleteByAttributeID(Long ID);
    AttributeDTO findAttributeByID(Long ID);
    List<AttributeDTO> findAllAttributes();

}
