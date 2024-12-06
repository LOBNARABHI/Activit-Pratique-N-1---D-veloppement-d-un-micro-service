package org.sid.banckservice.web;

import org.sid.banckservice.DTO.BankAccountRequestDTO;
import org.sid.banckservice.DTO.BankAccountResponseDTO;
import org.sid.banckservice.entities.BankAccount;
import org.sid.banckservice.entities.Customer;
import org.sid.banckservice.repositories.BankAccountRepository;
import org.sid.banckservice.repositories.CustomerRepository;
import org.sid.banckservice.services.AccountServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;

import java.util.Date;
import java.util.List;

@Controller
public class AccountGraphQLController {

    @Autowired
    private BankAccountRepository accountRepository;
    @Autowired
    private AccountServiceImpl accountServiceImpl;
    @Autowired
    private CustomerRepository customerRepository;

    @QueryMapping
    public List<BankAccount> accountsList()
    {
        return accountRepository.findAll();
    }


    @QueryMapping
    BankAccount accountById(@Argument String id)
    {
        return accountRepository.findById(id).orElseThrow(()->new RuntimeException(String.format("Account %s not found",id)));
    }

    @MutationMapping
    BankAccountResponseDTO addAccount(@Argument BankAccountRequestDTO bankAccountRequestDto)
    {
        return accountServiceImpl.addAccount(bankAccountRequestDto);
    }

    @MutationMapping
    BankAccountResponseDTO updateAccount(@Argument String id,@Argument BankAccountRequestDTO bankAccountRequestDto)
    {
        BankAccount bankAccount = new BankAccount();
        bankAccount=accountRepository.findById(id).orElseThrow(()->new RuntimeException(String.format("Account %s not found",id)));
        bankAccount.setCurrency(bankAccountRequestDto.getCurrency());
        bankAccount.setBalance(bankAccountRequestDto.getBalance());
        bankAccount.setType(bankAccountRequestDto.getType());
        bankAccount.setCreatedAt(new Date());

        accountRepository.save(bankAccount);

        return BankAccountResponseDTO.builder()
                .id(bankAccount.getId())
                .balance(bankAccount.getBalance())
                .createdAt(bankAccount.getCreatedAt())
                .currency(bankAccount.getCurrency())
                .type(bankAccount.getType())
                .build();
    }
    @MutationMapping
    boolean  deleteAccount(@Argument String id)
    {

        return accountServiceImpl.deleteAccount(id);
    }

    @QueryMapping
    public List<Customer> customers(){
        return customerRepository.findAll();
    }
}
