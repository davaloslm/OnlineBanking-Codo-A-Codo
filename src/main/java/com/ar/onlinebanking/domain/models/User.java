package com.ar.onlinebanking.domain.models;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
public class User {

    private Long id;

    private String username;

    private String password;


}
