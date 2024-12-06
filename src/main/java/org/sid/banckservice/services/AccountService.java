package org.sid.banckservice.services;

import org.sid.banckservice.DTO.BankAccountRequestDTO;
import org.sid.banckservice.DTO.BankAccountResponseDTO;
import org.sid.banckservice.entities.BankAccount;

public interface AccountService {
    public BankAccountResponseDTO addAccount(BankAccountRequestDTO bankAccountDTO);

    boolean deleteAccount(String id);
}
