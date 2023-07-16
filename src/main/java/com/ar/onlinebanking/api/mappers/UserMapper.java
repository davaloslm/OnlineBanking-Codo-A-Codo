package com.ar.onlinebanking.api.mappers;

import com.ar.onlinebanking.api.dtos.UserDto;
import com.ar.onlinebanking.domain.models.Account;
import com.ar.onlinebanking.domain.models.User;
import lombok.experimental.UtilityClass;

import java.util.ArrayList;
import java.util.List;

@UtilityClass
public class UserMapper {

    public User dtoToUser(UserDto dto){
        User user = new User();
        user.setUsername(dto.getUsername());
        user.setPassword(dto.getPassword());
        return user;
    }

    public UserDto userMapToDto(User user){
        UserDto dto = new UserDto();
        List<Long> accountsIds=new ArrayList<>();
        dto.setUsername(user.getUsername());
        dto.setPassword(user.getPassword());
        if (user.getAccounts()!=null)
            for (Account a:
                    user.getAccounts()) {
                Long id= a.getId();
                accountsIds.add(id);
            }

        dto.setAccounts(accountsIds);
        dto.setId(user.getId());
        return dto;
    }
}
