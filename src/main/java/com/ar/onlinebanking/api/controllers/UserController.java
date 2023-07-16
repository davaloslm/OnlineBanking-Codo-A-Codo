package com.ar.onlinebanking.api.controllers;

import com.ar.onlinebanking.api.dtos.UserDto;
import com.ar.onlinebanking.application.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class UserController {

    private UserService service;

    @Autowired
    public UserController(UserService service){
        this.service = service;
    }

    //HTTP GET METHOD

    //GET ALL USERS
    @GetMapping(value = "/users")
    public ResponseEntity<List<UserDto>> getUsers(){

        // 1) Obtener la lista de todos los user DTO de la DB
        List<UserDto> users = service.getUsers();

        // 2) Devolver la lista y enviar como respuesta
        return ResponseEntity.status(HttpStatus.OK).body(users);
    }

    //GET USER BY ID
    @GetMapping(value = "/users/{id}")
    public ResponseEntity<UserDto> getUserById(@PathVariable Long id){
        return ResponseEntity.status(HttpStatus.OK).body(service.getUserById(id));
    }

    //HTTP POST METHOD

    //Create user
    @PostMapping(value = "/users")
    public ResponseEntity<UserDto> createUser(@RequestBody UserDto dto){
        return ResponseEntity.status(HttpStatus.CREATED).body(service.createUser(dto));
    }

    // HTTP PUT METHOD

    //Update user

    @PutMapping(value = "/users/{id}")
    public ResponseEntity<UserDto> updateUser(@PathVariable Long id, @RequestBody UserDto user){
        return ResponseEntity.status(HttpStatus.OK).body(service.update(id, user));
    }

    // HTTP DELETE METHOD
    @DeleteMapping(value = "/users/{id}")
    public ResponseEntity<String> deleteUser(@PathVariable Long id){
        return ResponseEntity.status(HttpStatus.OK).body(service.delete(id));
    }
}
