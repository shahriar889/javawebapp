package com.julfiker.admin.dto;

import com.julfiker.admin.entity.Media;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class SellerDTO {
    private Long SellerID;
    private String name;
    private String description;
    private String address;
    private Long numRatings;
    private BigDecimal rating;
    private String socialMedia;
    private String returnPolicy;
    private LocalDateTime creation_date;
    private LocalDateTime last_updated;
    private Long mediaID;
    private Long userID;
    private List<Long> itemIDList;
    private List<Long> paymentMethodIDList;
}
