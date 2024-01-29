package com.julfiker.admin.entity;
import javax.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

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
    private String password;

    @ManyToMany(cascade = CascadeType.PERSIST, fetch = FetchType.EAGER)
    @JoinTable(
            name = "users_roles",
            joinColumns = @JoinColumn(name = "User_ID"),
            inverseJoinColumns = @JoinColumn(name = "Role_ID")
    )
    private Set<Role> roles = new HashSet<>();

    @Column(nullable = false)
    private String phone;

    @Column(nullable = false)
    private boolean status;

    @Column(nullable = false)
    private LocalDateTime creation_date;

    @Column
    private LocalDateTime last_updated;

    @OneToOne(mappedBy = "user", cascade = CascadeType.PERSIST, fetch = FetchType.EAGER)
    private Customer customer;

    @OneToOne(mappedBy = "user", cascade = CascadeType.PERSIST, fetch = FetchType.EAGER)
    private Seller seller;

}