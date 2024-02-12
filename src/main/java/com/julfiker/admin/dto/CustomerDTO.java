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
public class CustomerDTO {
    private Long customerID;
    private String name;
    private String description;
    private String address;
    private LocalDateTime lastOrderDate;
    private Long numOrders;
    private LocalDateTime creation_date;
    private LocalDateTime last_updated;
    private Long userID;
    private Long mediaID;
    private Long cartID;
    private Long orderID;
    private List<Long> paymentInfoIDs;
}
