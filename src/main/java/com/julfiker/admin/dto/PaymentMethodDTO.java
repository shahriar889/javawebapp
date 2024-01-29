package com.julfiker.admin.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class PaymentMethodDTO {
    private Long paymentMethodID;
    private String name;
    private List<Long> sellerIDList;
    private LocalDateTime creation_date;
    private LocalDateTime last_updated;
}
