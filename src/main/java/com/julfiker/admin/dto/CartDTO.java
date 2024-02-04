package com.julfiker.admin.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class CartDTO {
    private Long cartID;
    private List<Long> cartItemIDs;
    private BigDecimal totalPrice;
    private Long customerID;
    private LocalDateTime creation_date;
    private LocalDateTime last_updated;
}
