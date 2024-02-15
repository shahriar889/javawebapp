package com.julfiker.admin.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ShippingMethodDTO {
    private Long shippingMethodID;
    private String name;
    private String description;
    private Double Cost;
    private Integer estimatedDeliveryTime;
    private boolean isAvailable;
    private String trackingURL;
    private Integer handlingTime;
    private boolean isInternational;
    private List<Long> orderID;
    private LocalDateTime creation_date;
    private LocalDateTime last_updated;
}
