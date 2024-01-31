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
public class PaymentInfoDTO {
    private Long paymentInfoID;

    Long customerID;

    private String cardType;

    private String cardNumber;


    private String expirationDate;

    private String CVV;

    private LocalDateTime creation_date;

    private LocalDateTime last_updated;
}
