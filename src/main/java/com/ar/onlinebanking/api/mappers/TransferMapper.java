package com.ar.onlinebanking.api.mappers;

import com.ar.onlinebanking.api.dtos.TransferDto;
import com.ar.onlinebanking.domain.models.Transfer;
import lombok.experimental.UtilityClass;

@UtilityClass
public class TransferMapper {

    public Transfer dtoToTransfer(TransferDto dto){

        Transfer transfer = new Transfer();

        transfer.setDate(dto.getDate());
        transfer.setSender(dto.getSender());
        transfer.setReceiver(dto.getSender());
        transfer.setAmount(dto.getAmount());

        return transfer;
    }

    public TransferDto transferToDto(Transfer transfer){
        TransferDto dto = new TransferDto();

        dto.setDate(transfer.getDate());
        dto.setSender(transfer.getSender());
        dto.setReceiver(transfer.getReceiver());
        dto.setAmount(transfer.getAmount());
        dto.setId(transfer.getId());

        return dto;
    }
}
