package com.julfiker.admin.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CustomerUserDTOWrapper {
    private CustomerDTO customerDTO;
    private UserDto userDto;
}
