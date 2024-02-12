package com.julfiker.admin.dto;

import javax.validation.constraints.Email;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserDto
{
    private Long userID;
    private String name;
    @Email
    private String email;
    private String password;
    private Set<Long> roleIDSet;
    private LocalDateTime creation_Date;
    private LocalDateTime last_updated;
    private String phone;
    private Boolean status;
}