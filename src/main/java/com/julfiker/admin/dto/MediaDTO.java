package com.julfiker.admin.dto;

import com.julfiker.admin.entity.Customer;
import com.julfiker.admin.entity.Seller;
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
public class MediaDTO {
    private Long MediaID;
    private String fileOriginalPath;
    private String fileThumbnailPath;
    private String fileExtension;
    private Double fileSize;
    private LocalDateTime creation_date;
    private LocalDateTime last_updated;
    private List<Long> itemIDList;
    private Long customerID;
    private Long sellerID;
}
