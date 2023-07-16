package com.ar.onlinebanking.api.controllers;

import com.ar.onlinebanking.api.dtos.AccountDto;
import com.ar.onlinebanking.application.services.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class AccountController {

    private AccountService service;

    @Autowired
    public AccountController(AccountService service){
        this.service = service;
    }

    //HTTP GET METHOD

    //Get all accounts
    @GetMapping(value = "/accounts")
    public ResponseEntity<List<AccountDto>> getAccounts(){

        // Obtener la lista de todas las cuentas de la DB
        List<AccountDto> accounts = service.getAccounts();

        // Devolver la lista
        return ResponseEntity.status(HttpStatus.OK).body(accounts);
    }

    //Get account by ID
    @GetMapping(value = "/accounts/{id}")
    public ResponseEntity<AccountDto> getAccountById(@PathVariable Long id){

        AccountDto account = service.getAccountById(id);

        return ResponseEntity.status(HttpStatus.OK).body(account);
    }

    //HTTP POST METHOD

    //Create account
    @PostMapping(value = "/accounts")
    public ResponseEntity<AccountDto> createAccount(@RequestBody AccountDto dto){
        return ResponseEntity.status(HttpStatus.CREATED).body(service.createAccount(dto));
    }
    //HTTP PUT METHOD

    //Update account
    @PutMapping(value = "/accounts/{id}")
    public ResponseEntity<AccountDto> updateAccount(@PathVariable Long id, @RequestBody AccountDto account){
        return ResponseEntity.status(HttpStatus.OK).body(service.updateAccount(id, account));
    }

    //HTTP DELETE METHOD
    @DeleteMapping(value = "/accounts/{id}")
    public ResponseEntity<String> deleteAccount(@PathVariable Long id){
        return ResponseEntity.status(HttpStatus.OK).body(service.deleteAccount(id));
    }

}
