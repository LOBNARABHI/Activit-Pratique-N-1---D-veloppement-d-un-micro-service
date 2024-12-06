package org.sid.banckservice;

import org.sid.banckservice.entities.BankAccount;
import org.sid.banckservice.entities.Customer;
import org.sid.banckservice.enums.AccountType;
import org.sid.banckservice.repositories.BankAccountRepository;
import org.sid.banckservice.repositories.CustomerRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.Date;
import java.util.UUID;
import java.util.stream.Stream;

@SpringBootApplication
public class BanckServiceApplication {

    public static void main(String[] args) {

        SpringApplication.run(BanckServiceApplication.class, args);
    }

    @Bean
    CommandLineRunner start(BankAccountRepository bankAccountRepository, CustomerRepository customerRepository){
        return  args -> {
            Stream.of("Mohammed", "Lobna", "Hanae", "yassine").forEach(c->{
                Customer customer = Customer.builder()
                        .name(c)
                        .build();
                customerRepository.save(customer);
            });
            customerRepository.findAll().forEach(customer -> {
                for (int i = 0; i < 10; i++) {
                    BankAccount bankAccount=BankAccount.builder()
                            .id(UUID.randomUUID().toString())
                            .type(Math.random()>0.5? AccountType.CURRENT_ACCOUNT :AccountType.SAVING_ACCOUNT)
                            .balance(Math.random()*9000)
                            .createdAt(new Date())
                            .currency("MAD")
                            .customer(customer)
                            .build();
                    bankAccountRepository.save(bankAccount);
                }
            });

        };
    }


}
