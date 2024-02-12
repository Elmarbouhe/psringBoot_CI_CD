package ticseinfo3.samiri.ebankbackend.services;

import ticseinfo3.samiri.ebankbackend.dto.*;
import ticseinfo3.samiri.ebankbackend.exeptions.BankAccountNotFondException;
import ticseinfo3.samiri.ebankbackend.exeptions.BanlnceNotSufacientException;
import ticseinfo3.samiri.ebankbackend.exeptions.CustomerNotFundException;

import java.util.List;

public interface BankAccountService {

    CurrentBankAccountDTO addCurrentBankAccount(double initialBalence, double overDraft, Long customerId) throws CustomerNotFundException;
    SavingBankAccountDTO addSavingBankAccount(double initialBalence, double intrestRate , Long customerId) throws CustomerNotFundException;
    String deleteBankAccount(String accountId) throws BankAccountNotFondException;
    List<BankAccountDTO> getAllBankAccounts();
    BankAccountDTO getBankAccount(String accountId) throws BankAccountNotFondException;



    //account operations

}
