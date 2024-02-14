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
public class DeliveryManDTO {
    private Long deliveryManID;
    private String name;
    private String contactInfo;
    private boolean isAvailable;
    private String vehicleType;
    private String licensePlate;
    private BigDecimal rating;
    private Long numRatings;
    private Long shippingMethodID;
    private Long userID;
    private LocalDateTime creation_date;
    private LocalDateTime last_updated;
}
