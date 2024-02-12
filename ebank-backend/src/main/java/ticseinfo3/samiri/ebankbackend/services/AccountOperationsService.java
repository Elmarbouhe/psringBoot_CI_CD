package ticseinfo3.samiri.ebankbackend.services;

import ticseinfo3.samiri.ebankbackend.dto.AccountHistoryDTO;
import ticseinfo3.samiri.ebankbackend.dto.AccountOperationDTO;
import ticseinfo3.samiri.ebankbackend.exeptions.BankAccountNotFondException;
import ticseinfo3.samiri.ebankbackend.exeptions.BanlnceNotSufacientException;

import java.util.List;

public interface AccountOperationsService {
    void debit(String accountId, double amount, String description) throws BankAccountNotFondException, BanlnceNotSufacientException;
    void credit(String accountId, double amount, String description) throws BankAccountNotFondException;
    void transfer(String fromAccountId, String toAccountId, double amount,String description) throws BankAccountNotFondException, BanlnceNotSufacientException;
    List<AccountOperationDTO> accountHistory(String accountId);
    AccountHistoryDTO getAccountHistory(String accountId, int page, int size) throws BankAccountNotFondException;
}
