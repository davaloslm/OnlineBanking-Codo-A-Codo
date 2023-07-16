package com.ar.onlinebanking.application.services;

import com.ar.onlinebanking.api.dtos.UserDto;
import com.ar.onlinebanking.api.mappers.UserMapper;
import com.ar.onlinebanking.domain.exceptions.UserNotFoundException;
import com.ar.onlinebanking.domain.models.Account;
import com.ar.onlinebanking.domain.models.User;
import com.ar.onlinebanking.repositories.AccountRepository;
import com.ar.onlinebanking.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private UserRepository repository;

    @Autowired
    private AccountRepository accountRepository;

    public UserService(UserRepository repository,AccountRepository accountRepository){

        this.repository = repository;
        this.accountRepository=accountRepository;
    }

    //CRUD

    //Get all users
    public List<UserDto> getUsers(){
        List<User> users = repository.findAll();
        return users.stream()
                .map(UserMapper::userMapToDto)
                .toList();
    }

    //Get user by ID
    public UserDto getUserById(Long id){
        return UserMapper.userMapToDto(repository.findById(id).get());
    }

    //Create user
    public UserDto createUser(UserDto user){
        return UserMapper.userMapToDto(repository.save(UserMapper.dtoToUser(user)));
    }

    //Update user
    public UserDto update(Long id, UserDto user){

        Optional<User> createdUser = repository.findById(id);

        if (createdUser.isPresent()){
            User entity = createdUser.get();

            User updatedAccount = UserMapper.dtoToUser(user);
            updatedAccount.setAccounts(entity.getAccounts());

            if (user.getAccounts() != null) {
                List <Account> accountList = accountRepository.findAllById(user.getAccounts());
                List<Account> accountListFilter=accountList.stream().filter(e->!entity.getAccounts().contains(e)).toList();
                updatedAccount.getAccounts().addAll(accountListFilter);
                updatedAccount.setAccounts(accountList);
            }

            updatedAccount.setId(entity.getId());

            User updatedUser = repository.save(updatedAccount);

            return UserMapper.userMapToDto(updatedUser);
        } else {
            throw new UserNotFoundException("User with id: " + id + " not found");
        }
    }

    //Delete user
    public String delete(Long id){
        if (repository.existsById(id)){
            repository.deleteById(id);
            return "Se ha eliminado el usuario";
        } else {
            return "No se ha eliminado el usuario";
        }
    }


}
