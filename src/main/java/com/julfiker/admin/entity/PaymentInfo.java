package com.julfiker.admin.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "payment_infos")
public class PaymentInfo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "Payment_Info_ID")
    private Long paymentInfoID;

    @ManyToOne
    @JoinColumn(name = "Customer_ID", nullable = false)
    Customer customer;

    @Column(nullable = false)
    private String cardType;

    @Column(nullable = false)
    private String cardNumber;

    @Column(nullable = false)
    private String expirationDate;

    @Column(nullable = false)
    private String CVV;

    @Column(nullable = false)
    private LocalDateTime creation_date;

    @Column
    private LocalDateTime last_updated;
}
