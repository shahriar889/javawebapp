package com.julfiker.admin.manager;

import com.julfiker.admin.dto.Attributes_TypeDTO;
import com.julfiker.admin.entity.Attribute;
import com.julfiker.admin.repository.Attributes_TypeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.julfiker.admin.entity.Attributes_Type;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class Attributes_TypeManagerImpl implements Attributes_TypeManager {

    @Autowired
    Attributes_TypeRepository attributesTypeRepository;

    public Attributes_TypeDTO convertToDTO(Attributes_Type attributesType) {
        Attributes_TypeDTO attributesTypeDTO = new Attributes_TypeDTO();
        attributesTypeDTO.setAttributesTypeID(attributesType.getAttributesTypeID());
        attributesTypeDTO.setName(attributesType.getName());
        attributesTypeDTO.setCreation_date(attributesType.getCreation_date());
        if (attributesType.getLast_updated() != null)
            attributesTypeDTO.setLast_updated(attributesType.getLast_updated());
        List<Attribute> attributes = attributesType.getAttributes();
        List<Long> attributeIDs = new ArrayList<>();
        for (Attribute attribute : attributes)
            attributeIDs.add(attribute.getAttributeID());
        attributesTypeDTO.setAttributesID(attributeIDs);
        return attributesTypeDTO;
    }

    @Override
    public void saveAttributes_Type(Attributes_TypeDTO attributesTypeDTO) {
        if (attributesTypeDTO.getName() == null) {
            System.out.println("Need name to create attributes type");
            return;
        }
        Attributes_Type attributesType = new Attributes_Type();
        attributesType.setName(attributesTypeDTO.getName());
        attributesType.setCreation_date(LocalDateTime.now());
        List<Attribute> attributes = new ArrayList<>();
        attributesType.setAttributes(attributes);
        attributesTypeRepository.save(attributesType);
    }

    @Override
    public void updateAttributes_Type(Attributes_TypeDTO attributesTypeDTO, Long ID) {
        Attributes_Type attributesType = attributesTypeRepository.findByAttributesTypeID(ID);
        if (attributesType == null) {
            System.out.println("Could not find any Attributes_Type with is ID");
            return;
        }
        if (attributesTypeDTO.getName() != null) {
            attributesType.setName(attributesTypeDTO.getName());
            attributesType.setLast_updated(LocalDateTime.now());
            attributesTypeRepository.save(attributesType);
        }

    }

    @Override
    public void deleteAttributes_TypeByID(Long ID) {
        attributesTypeRepository.deleteByAttributesTypeID(ID);
    }

    @Override
    public List<Attributes_TypeDTO> findAll() {
        List<Attributes_Type> attributesTypes = attributesTypeRepository.findAll();
        List<Attributes_TypeDTO> attributesTypeDTOs = new ArrayList<>();
        for (Attributes_Type attributesType : attributesTypes)
            attributesTypeDTOs.add(convertToDTO(attributesType));
        return attributesTypeDTOs;
    }

    @Override
    public Attributes_TypeDTO findAttributes_TypeByID(Long ID) {
        Attributes_Type attributesType = attributesTypeRepository.findByAttributesTypeID(ID);
        if (attributesType == null) {
            System.out.println("Could not find Attributes_type with this ID");
            return new Attributes_TypeDTO();
        }
        return convertToDTO(attributesType);
    }


}