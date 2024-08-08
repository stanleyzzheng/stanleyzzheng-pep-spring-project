package com.example.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.example.entity.Account;
import com.example.exception.CustomException;
import com.example.exception.DuplicateUserException;
import com.example.repository.AccountRepository;

@Service
public class AccountService {
    private final AccountRepository accountRepository;

    @Autowired
    public AccountService(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    public void registerAccount(Account account) {
        if (accountRepository.existsByUsername(account.getUsername()))
            throw new DuplicateUserException("User already exists with username: " + account.getUsername());
        if (account.getUsername() == "") {
            throw new CustomException("Username should not be blank", HttpStatus.BAD_REQUEST);
        }
        if (account.getPassword().length() < 4)
            throw new CustomException("Password length must be greater than 4", HttpStatus.BAD_REQUEST);
        accountRepository.save(account);
    }

    public Account loginAccount(Account account) {
        Account user = accountRepository.findByUsername(account.getUsername())
                .orElseThrow(() -> new CustomException("Invalid Username or Password", HttpStatus.UNAUTHORIZED));
        if (!(user.getPassword().equals(account.getPassword()) ))
            throw new CustomException("Invalid Username or Password", HttpStatus.UNAUTHORIZED);
        // return ResponseEntity.ok(user);
        return user;
    }

}
