package com.ar.onlinebanking.api.mappers;

import com.ar.onlinebanking.api.dtos.AccountDto;
import com.ar.onlinebanking.api.dtos.UserDto;
import com.ar.onlinebanking.domain.models.Account;
import lombok.experimental.UtilityClass;

@UtilityClass
public class AccountMapper {

    public Account dtoToAccount(AccountDto dto){

        Account account = new Account();

        account.setBalance(dto.getAmount());

        return account;
    }

    public AccountDto AccountToDto(Account account){
        AccountDto dto = new AccountDto();
        dto.setAmount(account.getBalance());

        if (account.getOwner()!=null){
            UserDto userDto=UserMapper.userMapToDto(account.getOwner());
            dto.setOwner(userDto);
        }

        dto.setId(account.getId());
        return dto;
    }


}
