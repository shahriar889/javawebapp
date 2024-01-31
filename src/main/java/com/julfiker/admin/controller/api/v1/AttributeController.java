package com.julfiker.admin.controller.api.v1;

import com.julfiker.admin.dto.AttributeAttr_TypeDTOWrapper;
import com.julfiker.admin.dto.AttributeDTO;
import com.julfiker.admin.manager.AttributeManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1")
public class AttributeController {

    @Autowired
    AttributeManager attributeManager;

    @GetMapping("/attributes")
    public List<AttributeDTO> getAllAttributes(@RequestParam(required = false) String name) {
        return attributeManager.findAllAttributes().stream()
                .filter(attributeDTO -> name == null || attributeDTO.getName().equals(name))
                .collect(Collectors.toList());
    }

    @GetMapping("/attributes/{ID}")
    public AttributeDTO getAttributeByID(@PathVariable Long ID) {
        return attributeManager.findAttributeByID(ID);
    }


    @PostMapping("/attributes")
    public void createAttribute(@RequestBody AttributeAttr_TypeDTOWrapper wrapper) {
        attributeManager.saveAttribute(wrapper.getAttributeDTO(), wrapper.getAttributesTypeDTO());
    }

    @PutMapping("/attributes/{ID}")
    public void updateAttribute(@PathVariable Long ID, @RequestBody AttributeDTO attributeDTO) {
        attributeManager.updateAttribute(attributeDTO, ID);
    }

    @DeleteMapping("/attributes/{ID}")
    @Transactional
    public void deleteAttributeByID(@PathVariable Long ID) {
        attributeManager.deleteByAttributeID(ID);
    }
}
