package com.julfiker.admin.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "shipping_methods")
public class ShippingMethod {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "Shipping_Method_ID")
    private Long shippingMethodID;

    @Column(nullable = false, unique = true)
    private String name;

    @Column(nullable = false, columnDefinition = "Text")
    private String description;

    @Column(nullable = false)
    private Double cost;

    @Column(nullable = false)
    private Integer estimatedDeliveryTime;

    @Column(nullable = false)
    private boolean isAvailable;

    @Column(nullable = false, name = "tracking_url", unique = true)
    private String trackingURL;

    @Column(nullable = false)
    private Integer handlingTime;

    @Column(nullable = false)
    private boolean isInternational;

    @Column(nullable = false)
    private String courier;

    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "Delievry_Man_ID")
    DeliveryMan deliveryMan;

    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "OrderID")
    Order order;

    @Column(nullable = false)
    private LocalDateTime creation_date;

    @Column
    private LocalDateTime last_updated;
}
