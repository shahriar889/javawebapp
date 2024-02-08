package com.julfiker.admin.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.Future;
import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CategoryDto {
    private Long categoryID;
    private String name;
    private String description;
    private Long parentID;
    @Future(message = "Perishable must be a future date and time")
    private LocalDateTime perishable;
    private LocalDateTime creation_date;
    private LocalDateTime last_updated;
    private List<Long> itemIDs;
}
