package ticseinfo3.samiri.ebankbackend.services;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.CrossOrigin;
import ticseinfo3.samiri.ebankbackend.dto.*;
import ticseinfo3.samiri.ebankbackend.entities.*;
import ticseinfo3.samiri.ebankbackend.enums.OperationType;
import ticseinfo3.samiri.ebankbackend.exeptions.BankAccountNotFondException;
import ticseinfo3.samiri.ebankbackend.exeptions.BanlnceNotSufacientException;
import ticseinfo3.samiri.ebankbackend.exeptions.CustomerNotFundException;
import ticseinfo3.samiri.ebankbackend.mappers.BankAccountMapperImpl;
import ticseinfo3.samiri.ebankbackend.mappers.CustomerMapperImpl;
import ticseinfo3.samiri.ebankbackend.repositoies.AccountOperationRepository;
import ticseinfo3.samiri.ebankbackend.repositoies.BankAccountRepository;
import ticseinfo3.samiri.ebankbackend.repositoies.CustomerRepository;

import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Transactional
@Service
@AllArgsConstructor
@Slf4j

public class BankAccountServiceImpl implements BankAccountService {

    private CustomerRepository customerRepo;
    private BankAccountRepository bankAccountRepo;
    private AccountOperationRepository accountOperationRepo;
    private CustomerMapperImpl dtoMapper;
    private BankAccountMapperImpl bankAccountMapper;
    private CustomerService customer;

    // reusable function to find bank account
    protected BankAccount findBankAccount(String accountId) throws BankAccountNotFondException {
        BankAccount bankAccount = bankAccountRepo.findById(accountId).orElse(null);
        if (bankAccount == null) {
            throw new BankAccountNotFondException("Bank Account is not fond");
        }
        return bankAccount;
    }

    // add current bank account
    @Override
    public CurrentBankAccountDTO addCurrentBankAccount(double initialBalance, double overDraft, Long customerId) throws CustomerNotFundException {

        Customer customer = customerRepo.findById(customerId).orElse(null);
        if (customer == null)
            throw new CustomerNotFundException("Customer not found");
        CurrentAccount currentAccount = new CurrentAccount();
        currentAccount.setId(UUID.randomUUID().toString());
        currentAccount.setCreatedAt(new Date());
        currentAccount.setBalance(initialBalance);
        currentAccount.setOverDraft(overDraft);
        currentAccount.setCustomer(customer);
        CurrentAccount saveCurrentAccount = bankAccountRepo.save(currentAccount);

        return bankAccountMapper.fromCurrentAccToCurrentAccDTO(saveCurrentAccount);
    }

    // add SavingBankAccount
    @Override
    public SavingBankAccountDTO addSavingBankAccount(double initialBalence, double intrestRate, Long customerId) throws CustomerNotFundException {
        CustomerDTO customerDTO = customer.getCustomer(customerId);
        Customer customer = dtoMapper.fromCustomerDtoToCustomer(customerDTO);
        if (customer == null)
            throw new CustomerNotFundException("custumor not found");
        SavingAccount savingAccount = new SavingAccount();
        savingAccount.setId(UUID.randomUUID().toString());
        savingAccount.setCreatedAt(new Date());
        savingAccount.setBalance(intrestRate);
        savingAccount.setIntrestRate(intrestRate);
        savingAccount.setCustomer(customer);
        SavingAccount saveSavingAccount = bankAccountRepo.save(savingAccount);
        return bankAccountMapper.fromSavingAccToSavingDTO(saveSavingAccount);
    }

    //Delete BankAccount
    @Override
    public String deleteBankAccount(String accountId) throws BankAccountNotFondException {
        BankAccount bankAccount = this.findBankAccount(accountId);
        bankAccountRepo.delete(bankAccount);
        return "Account is deleted";
    }

    //Show all BankAccount
    @Override
    public List<BankAccountDTO> getAllBankAccounts() {
        List<BankAccount> bankAccountList = bankAccountRepo.findAll();
        List<BankAccountDTO> bankAccountDTOS = bankAccountList.stream().map(bankAccount -> {
            if (bankAccount instanceof SavingAccount) {
                SavingAccount savingAccount = (SavingAccount) bankAccount;
                return bankAccountMapper.fromSavingAccToSavingDTO(savingAccount);
            } else {
                CurrentAccount currentAccount = (CurrentAccount) bankAccount;
                return bankAccountMapper.fromCurrentAccToCurrentAccDTO(currentAccount);
            }
        }).collect(Collectors.toList());
        return bankAccountDTOS;
    }

    //show the BankAccount By id
    @Override
    public BankAccountDTO getBankAccount(String accountId) throws BankAccountNotFondException {
        BankAccount bankAccount = this.findBankAccount(accountId);
        if (bankAccount instanceof SavingAccount) {
            SavingAccount savingAccount = (SavingAccount) bankAccount;
            return bankAccountMapper.fromSavingAccToSavingDTO(savingAccount);
        } else {
            CurrentAccount currentAccount = (CurrentAccount) bankAccount;
            return bankAccountMapper.fromCurrentAccToCurrentAccDTO(currentAccount);
        }
    }

}
