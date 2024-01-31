package com.julfiker.admin.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class AttributeDTO {
    private Long AttributeID;
    private String name;
    private String description;
    private LocalDateTime creation_date;
    private LocalDateTime last_updated;
    private Long AttrTypeID;
    private List<Long> itemIDs;
}
