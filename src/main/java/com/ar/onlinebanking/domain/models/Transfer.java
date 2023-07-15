package com.ar.onlinebanking.domain.models;

import jakarta.persistence.*;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

@Entity
@Data
public class Transfer {

    private Long id;

    private Long sender;

    private Long receiver;

    private Date date;

    private BigDecimal amount;


}
