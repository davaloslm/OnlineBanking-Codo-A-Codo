package com.ar.onlinebanking.domain.models;

import jakarta.persistence.*;
import lombok.Data;

import java.math.BigDecimal;

@Entity
@Data
public class Account {

    private Long id;

    private BigDecimal balance;

    private User owner;
}
