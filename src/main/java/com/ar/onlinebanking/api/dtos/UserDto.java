package com.ar.onlinebanking.api.dtos;

import lombok.Data;

import java.util.List;

@Data
public class UserDto {

    public UserDto(){}

    private Long id;

    private String username;

    private String password;

    private List<Long> accounts;
}
