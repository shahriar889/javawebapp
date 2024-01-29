package com.julfiker.admin.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name="sellers")
public class Seller {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "Seller_ID")
    private Long sellerID;

    @Column(nullable = false, unique = true)
    private String name;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String description;

    @Column(nullable = false)
    private BigDecimal rating;

    @Column(nullable = false)
    private Long numRatings;

    @Column(nullable = false, unique = true)
    private String socialMedia;

    @Column(nullable = false, columnDefinition = "TEXT", unique = true)
    private String address;


    @Column(nullable = false, columnDefinition = "TEXT")
    private String returnPolicy;

    @Column(nullable = false)
    private LocalDateTime creation_date;

    @Column
    private LocalDateTime last_updated;

    @OneToOne(cascade = CascadeType.PERSIST, fetch = FetchType.EAGER)
    @JoinColumn(name = "media_id", referencedColumnName = "media_id")
    private Media media;

    @OneToOne(cascade = CascadeType.PERSIST, fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private User user;


    @OneToMany(mappedBy = "seller")
    private List<Item> items;

    @ManyToMany(cascade = CascadeType.PERSIST, fetch = FetchType.EAGER)
    @JoinTable(
            name = "seller_payment_methods",
            joinColumns = @JoinColumn(name = "Seller_ID"),
            inverseJoinColumns = @JoinColumn(name = "Payment_Method_ID")
    )
    private Set<PaymentMethod> paymentMethods = new HashSet<>();
}
