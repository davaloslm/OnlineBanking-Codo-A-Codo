package com.ar.onlinebanking.api.dtos;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class AccountDto {
    private Long id;

    private BigDecimal amount;

    private UserDto owner;

}
