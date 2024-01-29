package com.julfiker.admin.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ItemTypeDTO {
    private Long itemTypeID;
    private String name;
    private String description;
    private LocalDateTime creation_date;
    private LocalDateTime last_updated;
    private List<Long> itemIDList;
}
