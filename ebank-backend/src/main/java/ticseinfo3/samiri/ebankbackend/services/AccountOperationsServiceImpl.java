package ticseinfo3.samiri.ebankbackend.services;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ticseinfo3.samiri.ebankbackend.dto.AccountHistoryDTO;
import ticseinfo3.samiri.ebankbackend.dto.AccountOperationDTO;
import ticseinfo3.samiri.ebankbackend.entities.AccountOperation;
import ticseinfo3.samiri.ebankbackend.entities.BankAccount;
import ticseinfo3.samiri.ebankbackend.enums.OperationType;
import ticseinfo3.samiri.ebankbackend.exeptions.BankAccountNotFondException;
import ticseinfo3.samiri.ebankbackend.exeptions.BanlnceNotSufacientException;
import ticseinfo3.samiri.ebankbackend.mappers.BankAccountMapperImpl;
import ticseinfo3.samiri.ebankbackend.repositoies.AccountOperationRepository;
import ticseinfo3.samiri.ebankbackend.repositoies.BankAccountRepository;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Transactional
@Service
@AllArgsConstructor
@Slf4j
public class AccountOperationsServiceImpl implements AccountOperationsService{

    private BankAccountServiceImpl bankAccountServiceImpl;
    private AccountOperationRepository accountOperationRepo;
    private BankAccountRepository bankAccountRepo;
    private BankAccountMapperImpl bankAccountMapper;
    @Override
    public void debit(String accountId, double amount, String description) throws BankAccountNotFondException, BanlnceNotSufacientException {
        BankAccount bankAccount = bankAccountServiceImpl.findBankAccount(accountId);
        if (bankAccount.getBalance()<amount)
            throw new BanlnceNotSufacientException("the balance is not suficient");
        else if (bankAccount.getBalance() >= amount) {
            AccountOperation accountOperation = new AccountOperation();
            accountOperation.setType(OperationType.DEBIT);
            accountOperation.setAmount(amount);
            accountOperation.setDescription(description);
            accountOperation.setOperationDate(new Date());
            accountOperation.setBankAccount(bankAccount);
            accountOperationRepo.save(accountOperation);
            bankAccount.setBalance(bankAccount.getBalance()-amount);
            bankAccountRepo.save(bankAccount);
        }
    }

    @Override
    public void credit(String accountId, double amount, String description) throws BankAccountNotFondException {
        BankAccount bankAccount = bankAccountServiceImpl.findBankAccount(accountId);
        AccountOperation accountOperation = new AccountOperation();
        accountOperation.setType(OperationType.CREDIT);
        accountOperation.setAmount(amount);
        accountOperation.setDescription(description);
        accountOperation.setOperationDate(new Date());
        accountOperation.setBankAccount(bankAccount);
        accountOperationRepo.save(accountOperation);
        bankAccount.setBalance(bankAccount.getBalance()+amount);
        bankAccountRepo.save(bankAccount);
    }


    @Override
    public void transfer(String fromAccountId, String toAccountId, double amount,String description) throws BankAccountNotFondException, BanlnceNotSufacientException {
        debit(fromAccountId,amount,"transfer to "+toAccountId+" "+description);
        credit(toAccountId,amount,"transfer from "+fromAccountId+" "+description);
    }

    @Override
    public List<AccountOperationDTO> accountHistory(String accountId){
        List<AccountOperation> accountOperations =accountOperationRepo.findByBankAccountId(accountId);
        return   accountOperations.stream().map(op->
                bankAccountMapper.fromAccountOperationToAccountOperationDTO(op)).collect(Collectors.toList());
    }


    @Override
    public AccountHistoryDTO getAccountHistory(String accountId, int page, int size) throws BankAccountNotFondException {
        BankAccount account = bankAccountServiceImpl.findBankAccount(accountId);
        Page<AccountOperation> accountOperations = accountOperationRepo.findByBankAccountIdOrderByOperationDateDesc(accountId, PageRequest.of(page,size));
        AccountHistoryDTO accountHistoryDTO = new AccountHistoryDTO();
        List<AccountOperationDTO> accountOperationDTOS = accountOperations.getContent().stream().map(op-> bankAccountMapper.fromAccountOperationToAccountOperationDTO(op)).collect(Collectors.toList());
        accountHistoryDTO.setAccountOperationDTOS(accountOperationDTOS);
        accountHistoryDTO.setAccountId(account.getId());
        accountHistoryDTO.setBalance(account.getBalance());
        accountHistoryDTO.setCurrentPage(page);
        accountHistoryDTO.setPageSize(size);
        accountHistoryDTO.setTotalPages(accountOperations.getTotalPages());

        return  accountHistoryDTO;
    }
}
