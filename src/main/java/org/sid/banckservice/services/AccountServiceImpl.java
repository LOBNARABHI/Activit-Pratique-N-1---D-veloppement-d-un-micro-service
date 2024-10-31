package org.sid.banckservice.services;

import org.sid.banckservice.DTO.BankAccountRequestDTO;
import org.sid.banckservice.DTO.BankAccountResponseDTO;
import org.sid.banckservice.entities.BankAccount;
import org.sid.banckservice.mappers.AccountMapper;
import org.sid.banckservice.repositories.BankAccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.UUID;

@Service
@Transactional
public class AccountServiceImpl implements AccountService {
    @Autowired
    private BankAccountRepository bankAccountRepository;
    @Autowired
    private AccountMapper accountMapper;
    @Override
    public BankAccountResponseDTO addAccount(BankAccountRequestDTO bankAccountDTO) {
        BankAccount bankAccount=BankAccount.builder()
                .id(UUID.randomUUID().toString())
                .createdAt(new Date())
                .balance(bankAccountDTO.getBalance())
                .type(bankAccountDTO.getType())
                .currency(bankAccountDTO.getCurrency())
                .build();
        BankAccount savedBankAccount = bankAccountRepository.save(bankAccount);

       BankAccountResponseDTO bankAccountResponseDTO =  accountMapper.fromBankAccount(savedBankAccount);
        return bankAccountResponseDTO;
    }
}
