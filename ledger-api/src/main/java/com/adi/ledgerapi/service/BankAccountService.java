package com.adi.ledgerapi.service;

import com.adi.ledgerapi.model.BankAccount;
import com.adi.ledgerapi.repository.BankAccountRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.math.BigDecimal;

@Service
public class BankAccountService {

    private final BankAccountRepository repository;


    public BankAccountService(BankAccountRepository repository) {
        this.repository = repository;
    }

    public BankAccount createAccount(BankAccount account) {
        return repository.save(account);
    }

    @Transactional
    public void transferMoney(String fromAccNumber, String toAccNumber, BigDecimal amount) {
        BankAccount fromAcc = repository.findByAccountNumber(fromAccNumber)
                .orElseThrow(() -> new RuntimeException("Source account not found"));

        BankAccount toAcc = repository.findByAccountNumber(toAccNumber)
                .orElseThrow(() -> new RuntimeException("Destination account not found"));

        if (fromAcc.getBalance().compareTo(amount) < 0) {
            throw new RuntimeException("Insufficient funds");
        }

        fromAcc.setBalance(fromAcc.getBalance().subtract(amount));
        toAcc.setBalance(toAcc.getBalance().add(amount));

        repository.save(fromAcc);
        repository.save(toAcc);
    }
}