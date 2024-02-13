package com.julfiker.admin.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class OrderItemDTO {
    private Long orderItemID;
    private Long itemID;
    private Long orderID;
    private Integer quantity;
    private BigDecimal subTotal;
    private LocalDateTime creation_date;
    private LocalDateTime last_updated;
}

