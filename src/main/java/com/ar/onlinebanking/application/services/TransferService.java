package com.ar.onlinebanking.application.services;

import com.ar.onlinebanking.api.dtos.TransferDto;
import com.ar.onlinebanking.api.mappers.TransferMapper;
import com.ar.onlinebanking.application.exceptions.InsufficientFundsException;
import com.ar.onlinebanking.domain.exceptions.AccountNotFoundException;
import com.ar.onlinebanking.domain.exceptions.TransferNotFoundException;
import com.ar.onlinebanking.domain.models.Account;
import com.ar.onlinebanking.domain.models.Transfer;
import com.ar.onlinebanking.repositories.AccountRepository;
import com.ar.onlinebanking.repositories.TransferRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class TransferService {

    @Autowired
    private TransferRepository repository;

    @Autowired
    private AccountRepository accountRepository;

    public TransferService(TransferRepository repository){
        this.repository = repository;
    }

    //CRUD

    //Get all transfers

    public List<TransferDto> getTransfers(){
        List<Transfer> transfers = repository.findAll();
        return transfers.stream()
                .map(TransferMapper::transferToDto)
                .collect(Collectors.toList());
    }

    //Get transfer by ID
    public TransferDto getTransferById(Long id){

        Transfer transfer = repository.findById(id).orElseThrow(() ->
                new TransferNotFoundException("Transfer  with id: " + id + " not found"));

        return TransferMapper.transferToDto(transfer);
    }

    //Create transfer
    @Transactional
    public TransferDto transfer(TransferDto dto) {

        // Comprobar si existen la cuenta que envía y la que recibe dinero

        Account sender = accountRepository.findById(dto.getSender())
                .orElseThrow(() -> new AccountNotFoundException("Account with id: " + dto.getSender() + " not found"));

        Account receiver = accountRepository.findById(dto.getReceiver())
                .orElseThrow(() -> new AccountNotFoundException("Account with id: " + dto.getReceiver() + " not found"));

        // Comprobar si la cuenta de origen tiene fondos suficientes
        if (sender.getBalance().compareTo(dto.getAmount()) < 0) {
            throw new InsufficientFundsException("Insufficient funds in the account with id: " + dto.getSender());
        }

        // Realizar la transferencia
        sender.setBalance(sender.getBalance().subtract(dto.getAmount()));
        receiver.setBalance(receiver.getBalance().add(dto.getAmount()));

        // Guardar las cuentas actualizadas
        accountRepository.save(sender);
        accountRepository.save(receiver);

        // Crear la transferencia y guardarla en la base de datos
        Transfer transfer = new Transfer();



        // Creamos un objeto del tipo Date para obtener la fecha actual
        Date date = new Date();

        // Seteamos el objeto fecha actual en el transferDto
        transfer.setDate(date);

        transfer.setSender(sender.getId());
        transfer.setReceiver(receiver.getId());
        transfer.setAmount(dto.getAmount());

        transfer = repository.save(transfer);

        // Devolver el DTO de la transferencia realizada
        return TransferMapper.transferToDto(transfer);
    }

    //Update transfer
    public TransferDto updateTransfer(Long id, TransferDto transferDto){

        Transfer transfer = repository.findById(id).orElseThrow(() -> new TransferNotFoundException("Transfer not found with id: " + id));

        Transfer updatedTransfer = TransferMapper.dtoToTransfer(transferDto);

        updatedTransfer.setId(transfer.getId());

        return TransferMapper.transferToDto(repository.save(updatedTransfer));
    }

    //Delete transfer
    public String deleteTransfer(Long id){
        if (repository.existsById(id)){
            repository.deleteById(id);
            return "Se ha eliminado la transferencia";
        } else {
            return "La transferencia no existe";
        }
    }
}
