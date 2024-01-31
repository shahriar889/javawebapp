package com.julfiker.admin.manager;

import com.julfiker.admin.dto.Attributes_TypeDTO;

import java.util.List;

public interface Attributes_TypeManager {
    void saveAttributes_Type(Attributes_TypeDTO attributesTypeDTO);
    void updateAttributes_Type(Attributes_TypeDTO attributesTypeDTO, Long ID);
    void deleteAttributes_TypeByID(Long ID);
    List<Attributes_TypeDTO> findAll();
    Attributes_TypeDTO findAttributes_TypeByID(Long ID);
}
