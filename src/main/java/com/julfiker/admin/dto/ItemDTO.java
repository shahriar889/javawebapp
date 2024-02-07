package com.julfiker.admin.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ItemDTO {

    private Long itemID;
    private String name;
    private String description;
    private BigDecimal price;
    private BigDecimal rating;
    private Long num_ratings;
    private Long stock_quantity;
    private Long sellerID;
    private Long itemTypeID;
    private Set<Long> categoryIDs;
    private Set<Long> mediaIDs;
    private Long displayMediaID;
    private Set<Long> attributeIDs;
    private List<Long> cartItemIDs;
    private List<Long> orderItemIDs;
    private LocalDateTime creation_date;
    private LocalDateTime last_updated;

}
