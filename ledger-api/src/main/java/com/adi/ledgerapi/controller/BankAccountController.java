package com.adi.ledgerapi.controller;

import com.adi.ledgerapi.dto.TransferRequest;
import com.adi.ledgerapi.model.BankAccount;
import com.adi.ledgerapi.service.BankAccountService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/v1/accounts")
public class BankAccountController {

    private final BankAccountService accountService;

    public BankAccountController(BankAccountService accountService) {
        this.accountService = accountService;
    }

    @PostMapping
    public BankAccount createAccount(@RequestBody BankAccount account) {
        return accountService.createAccount(account);
    }

    @PostMapping("/transfers")
    public String transferMoney(@Valid @RequestBody TransferRequest request) {
        accountService.transferMoney(
                request.getSourceAccountNumber(),
                request.getDestinationAccountNumber(),
                request.getAmount()
        );
        return "Transfer successful!";
    }
}