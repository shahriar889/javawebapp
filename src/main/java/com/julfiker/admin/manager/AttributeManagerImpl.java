package com.julfiker.admin.manager;

import com.julfiker.admin.dto.AttributeDTO;
import com.julfiker.admin.dto.Attributes_TypeDTO;
import com.julfiker.admin.entity.Attribute;
import com.julfiker.admin.entity.Attributes_Type;
import com.julfiker.admin.entity.Item;
import com.julfiker.admin.repository.AttributeRepository;
import com.julfiker.admin.repository.Attributes_TypeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class AttributeManagerImpl implements AttributeManager{

    @Autowired
    AttributeRepository attributeRepository;
    @Autowired
    Attributes_TypeRepository attributesTypeRepository;

    private AttributeDTO convertToDTO(Attribute attribute){
        AttributeDTO attributeDTO = new AttributeDTO();
        attributeDTO.setAttributeID(attribute.getAttributeID());
        attributeDTO.setName(attribute.getName());
        attributeDTO.setDescription(attribute.getDescription());
        attributeDTO.setAttrTypeID(attribute.getAttributesType().getAttributesTypeID());
        attributeDTO.setCreation_date(attribute.getCreation_date());
        if(attribute.getLast_updated() != null)
            attributeDTO.setLast_updated(attribute.getLast_updated());
        List<Long> itemIDs = new ArrayList<>();
        List<Item> items = attribute.getItems();
        for(Item item : items)
            itemIDs.add(item.getItemID());
        attributeDTO.setItemIDs(itemIDs);
        return attributeDTO;
    }

    @Override
    public void saveAttribute(AttributeDTO attributeDTO, Attributes_TypeDTO attrTypesDTO){
        if(attributeDTO.getName() == null || attributeDTO.getDescription() == null
        || attrTypesDTO.getName() == null){
            System.out.println("Not enough info to make Attribute");
            return;
        }
        Attribute attribute = new Attribute();
        attribute.setName(attributeDTO.getName());
        attribute.setDescription(attributeDTO.getDescription());
        attribute.setCreation_date(LocalDateTime.now());
        Attributes_Type attributesType = attributesTypeRepository.findByName(attrTypesDTO.getName());
        if(attributesType == null){
            attributesType = new Attributes_Type();
            attributesType.setName(attrTypesDTO.getName());
            attributesType.setCreation_date(LocalDateTime.now());
            attributesType.setAttributes(new ArrayList<>());
        }
        attribute.setAttributesType(attributesType);
        attributesType.getAttributes().add(attribute);
        attributeRepository.save(attribute);
        attributesTypeRepository.save(attributesType);
    }
    @Override
    public void updateAttribute(AttributeDTO attributeDTO, Long ID){
        Attribute attribute = attributeRepository.findByAttributeID(ID);
        if(attribute == null){
            System.out.println("Could not find any Attributes with is ID");
            return;
        }
        if(attributeDTO.getName() != null)
            attribute.setName(attributeDTO.getName());
        if(attributeDTO.getDescription() != null)
            attribute.setDescription(attributeDTO.getDescription());
        attribute.setLast_updated(LocalDateTime.now());
        attributeRepository.save(attribute);
    }
    @Override
    @Transactional
    public void deleteByAttributeID(Long ID){
        attributeRepository.deleteByAttributeID(ID);
    }
    @Override
    public AttributeDTO findAttributeByID(Long ID){
        Attribute attribute = attributeRepository.findByAttributeID(ID);
        if(attribute == null){
            System.out.println("could not find attribute with this ID");
            return new AttributeDTO();
        }
        return convertToDTO(attribute);
    }
    @Override
    public List<AttributeDTO> findAllAttributes(){
        List<Attribute> attributes = attributeRepository.findAll();
        List<AttributeDTO> attributeDTOS = new ArrayList<>();
        for(Attribute attribute : attributes)
            attributeDTOS.add(convertToDTO(attribute));
        return attributeDTOS;
    }
}
