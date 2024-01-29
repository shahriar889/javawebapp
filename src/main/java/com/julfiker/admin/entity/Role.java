package com.julfiker.admin.entity;


import javax.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name="roles")
public class Role
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "Role_ID")
    private Long roleID;

    @Column(nullable=false, unique=true)
    private String name;

    @Column(nullable = false)
    private LocalDateTime creation_date;

    @Column
    private LocalDateTime last_updated;

    @ManyToMany(mappedBy="roles")
    private List<User> users;
}
