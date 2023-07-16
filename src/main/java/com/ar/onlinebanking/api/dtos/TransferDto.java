package com.ar.onlinebanking.api.dtos;

import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

@Data
public class TransferDto {

    private Long id;

    private Long sender;

    private Long receiver;

    private Date date;

    private BigDecimal amount;
}
