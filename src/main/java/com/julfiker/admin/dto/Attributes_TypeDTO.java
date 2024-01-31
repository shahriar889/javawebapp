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
public class Attributes_TypeDTO {
    private Long attributesTypeID;
    private String name;
    private LocalDateTime creation_date;
    private LocalDateTime last_updated;
    private List<Long> attributesID;
}
