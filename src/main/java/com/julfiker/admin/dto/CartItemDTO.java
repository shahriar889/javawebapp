package com.julfiker.admin.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CartItemDTO {
    private Long CartItemID;
    private Long ItemID;
    private Long CartID;
    private Integer quantity;
    private LocalDateTime create_date;
    private LocalDateTime last_updated;
}
