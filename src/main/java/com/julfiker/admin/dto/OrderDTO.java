package com.julfiker.admin.dto;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.internal.util.collections.Stack;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class OrderDTO {
    private Long orderID;
    private List<Long> orderItemIDs;
    private BigDecimal totalPrice;
    private Long customerID;
    private Long shippingMethodID;
    private LocalDateTime creation_date;
    private LocalDateTime last_updated;
    private String invoiceID;
    private  boolean status;
    private LocalDateTime deliverDate;
    private Long deliveryManID;
}
