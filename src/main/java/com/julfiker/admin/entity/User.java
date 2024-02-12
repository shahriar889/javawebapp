package com.julfiker.admin.entity;
import javax.persistence.*;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.jpa.repository.Temporal;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import static javax.persistence.TemporalType.TIMESTAMP;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name="users")
public class User
{
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "User_ID")
    private Long userID;

    @Column(nullable=false)
    private String name;

    @Column(nullable=false, unique=true)
    private String email;

    @Column(nullable=false)
    @Size(min = 8, message = "Password must be at least 8 characters long")
    @Pattern(regexp = "^(?=.*[0-9])(?=.*[a-zA-Z])(?=.*[@#$%^&+=!\\-]).*$", message = "Password must contain at least one number, one alphabet, and one of the following special characters: @#$%^&+=!-")
    private String password;

    @ManyToMany(cascade = CascadeType.PERSIST, fetch = FetchType.EAGER)
    @JoinTable(
            name = "users_roles",
            joinColumns = @JoinColumn(name = "User_ID"),
            inverseJoinColumns = @JoinColumn(name = "Role_ID")
    )
    private Set<Role> roles = new HashSet<>();

    @Column(nullable = false, unique = true)
    private String phone;

    @Column(nullable = false)
    private Boolean status;

    @Column(nullable = false, columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    private LocalDateTime creation_date = LocalDateTime.now();

    @Column
    private LocalDateTime last_updated;

    @OneToOne(mappedBy = "user", cascade = CascadeType.PERSIST, fetch = FetchType.EAGER)
    private Customer customer;

    @OneToOne(mappedBy = "user", cascade = CascadeType.PERSIST, fetch = FetchType.EAGER)
    private Seller seller;

    @OneToOne(mappedBy = "user", cascade = CascadeType.PERSIST, fetch = FetchType.EAGER)
    private DeliveryMan deliveryMan;

}