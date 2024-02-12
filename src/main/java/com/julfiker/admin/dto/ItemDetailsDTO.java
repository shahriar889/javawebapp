package com.julfiker.admin.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ItemDetailsDTO {
    private Long itemDetailsID;
    private String description;
    private Long itemID;
    private LocalDateTime creation_date;
    private LocalDateTime last_updated;
}
