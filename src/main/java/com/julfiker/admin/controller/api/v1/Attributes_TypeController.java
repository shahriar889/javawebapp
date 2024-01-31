package com.julfiker.admin.controller.api.v1;

import com.julfiker.admin.dto.Attributes_TypeDTO;
import com.julfiker.admin.manager.Attributes_TypeManager;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;


import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1")
public class Attributes_TypeController {
    @Autowired
    Attributes_TypeManager attributesTypeManager;

    @GetMapping("/attributes-types")
    public List<Attributes_TypeDTO> getAllAttributes_Types(@RequestParam(required = false) String name) {
        return attributesTypeManager.findAll().stream()
                .filter(attributesTypeDTO -> name == null || attributesTypeDTO.getName().equals(name))
                .collect(Collectors.toList());
    }

    @GetMapping("/attributes-types/{ID}")
    public Attributes_TypeDTO getAttributes_typeByID(@PathVariable Long ID) {
        return attributesTypeManager.findAttributes_TypeByID(ID);
    }

    @PostMapping("/attributes-types")
    public void createAttributes_Type(@RequestBody Attributes_TypeDTO attributesTypeDTO) {
        attributesTypeManager.saveAttributes_Type(attributesTypeDTO);
    }

    @PutMapping("/attributes-types/{ID}")
    public void updateAttributes_Type(@PathVariable Long ID, @RequestBody Attributes_TypeDTO attributesTypeDTO) {
        attributesTypeManager.updateAttributes_Type(attributesTypeDTO, ID);
    }

    @DeleteMapping("/attributes-types/{ID}")
    @Transactional
    public void deleteAttributes_TypeByID(@PathVariable Long ID) {
        attributesTypeManager.deleteAttributes_TypeByID(ID);
    }
}
