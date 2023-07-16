package com.ar.onlinebanking.application.services;

import com.ar.onlinebanking.api.dtos.AccountDto;
import com.ar.onlinebanking.api.mappers.AccountMapper;
import com.ar.onlinebanking.application.exceptions.InsufficientFundsException;
import com.ar.onlinebanking.domain.exceptions.AccountNotFoundException;
import com.ar.onlinebanking.domain.exceptions.UserNotFoundException;
import com.ar.onlinebanking.domain.models.Account;
import com.ar.onlinebanking.domain.models.User;
import com.ar.onlinebanking.repositories.AccountRepository;
import com.ar.onlinebanking.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Service
public class AccountService {

    @Autowired
    private AccountRepository repository;
    @Autowired
    private UserRepository userRepository;

    public AccountService(AccountRepository repository, UserRepository userRepository) {
        this.repository = repository;
        this.userRepository = userRepository;
    }

    //CRUD

    //Get all accounts
    @Transactional
    public List<AccountDto> getAccounts() {
        List<Account> accounts = repository.findAll();
        return accounts.stream()
                .map(AccountMapper::AccountToDto)
                .toList();
    }

    //Get account by ID
    @Transactional
    public AccountDto getAccountById(Long id) {
        AccountDto account = AccountMapper.AccountToDto(repository.findById(id).get());

        return account;
    }

    //Create account

    @Transactional
    public AccountDto createAccount(AccountDto account) {
        Optional<User> user = userRepository.findById(account.getOwner().getId());

        Account accountModel = AccountMapper.dtoToAccount(account);

        accountModel.setOwner(user.get());

        accountModel = repository.save(accountModel);

        AccountDto dto = AccountMapper.AccountToDto(accountModel);

        return dto;
    }

    //Update account
    @Transactional
    public AccountDto updateAccount(Long id, AccountDto account) {

        Optional<Account> createdAccount = repository.findById(id);

        if (createdAccount.isPresent()) { //si la cuenta existe en la base de datos

            Account entity = createdAccount.get();

            if (account.getAmount() != null) { //si en el body el monto no es nulo entonces debe modificarse
                entity.setBalance(account.getAmount());
            }
            if (account.getOwner() != null) { //si en el body el titular no es nulo entonces debe modificarse

                User user = userRepository.getReferenceById(account.getOwner().getId());
                if (user != null) {
                    entity.setOwner(user);
                }

            }

            Account saved = repository.save(entity);

            return AccountMapper.AccountToDto(saved);

        } else {
            throw new AccountNotFoundException("Account not found with id: " + id);
        }
    }

    //Delete account
    @Transactional
    public String deleteAccount(Long id){

        if (repository.existsById(id)){
            repository.deleteById(id);
            return "Se ha eliminado la cuenta";
        } else {
            return "No se ha eliminado la cuenta";
        }

    }

    //Métodos para debitar o acreditar dinero en una transferencia
    public BigDecimal deduct(BigDecimal amount, Long accountId){

        // primero obtenemos la cuenta que envía el dinero
        Account account = repository.findById(accountId).orElse(null);

        // luego debitamos el valor del monto a enviar al saldo de esa cuenta
        if (account.getBalance().subtract(amount).intValue() > 0){
            account.setBalance(account.getBalance().subtract(amount));
            repository.save(account);
        }

        //  el método retorna el monto debitado
        return account.getBalance().subtract(amount);
    }

    public BigDecimal add(BigDecimal amount, Long accountId){

        // primero obtenemos la cuenta que recibe el dinero
        Account account = repository.findById(accountId).orElse(null);

        // luego agregamos el valor del monto al saldo de esa cuenta
        account.setBalance(account.getBalance().add(amount));
        repository.save(account);

        // el método retorna el monto acreditado
        return amount;
    }

}
