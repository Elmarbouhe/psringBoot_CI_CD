package ticseinfo3.samiri.ebankbackend.mappers;


import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ticseinfo3.samiri.ebankbackend.dto.AccountOperationDTO;
import ticseinfo3.samiri.ebankbackend.dto.CurrentBankAccountDTO;
import ticseinfo3.samiri.ebankbackend.dto.SavingBankAccountDTO;
import ticseinfo3.samiri.ebankbackend.entities.AccountOperation;
import ticseinfo3.samiri.ebankbackend.entities.CurrentAccount;
import ticseinfo3.samiri.ebankbackend.entities.SavingAccount;

@Service
public class BankAccountMapperImpl {

    @Autowired
    private CustomerMapperImpl customerMapper;


    public SavingBankAccountDTO fromSavingAccToSavingDTO(SavingAccount savingAccount){
        SavingBankAccountDTO bankAccountDTO = new SavingBankAccountDTO();
        BeanUtils.copyProperties(savingAccount,bankAccountDTO);
        bankAccountDTO.setCustomerDTO(customerMapper.fromCustomerToCustomerDTO(savingAccount.getCustomer()));
        bankAccountDTO.setType(savingAccount.getClass().getSimpleName());
        return bankAccountDTO;
    }

    public SavingAccount fromSavingDtoToSavingAcc(SavingBankAccountDTO savingBankAccountDTO){
        SavingAccount savingAccount = new SavingAccount();
        BeanUtils.copyProperties(savingBankAccountDTO,savingAccount);
        savingAccount.setCustomer(customerMapper.fromCustomerDtoToCustomer(savingBankAccountDTO.getCustomerDTO()));
        return savingAccount;
    }

    public CurrentBankAccountDTO  fromCurrentAccToCurrentAccDTO(CurrentAccount currentAccount){
        CurrentBankAccountDTO currentBankAccountDTO  = new CurrentBankAccountDTO();
        BeanUtils.copyProperties(currentAccount, currentBankAccountDTO);
        currentBankAccountDTO.setCustomerDTO(customerMapper.fromCustomerToCustomerDTO(currentAccount.getCustomer()));
        currentBankAccountDTO.setType(currentAccount.getClass().getSimpleName());
        return currentBankAccountDTO;
    }

    public CurrentAccount fromCurrentAccDtoToCurrentAcc(CurrentBankAccountDTO currentBankAccountDTO){
        CurrentAccount currentAccount = new CurrentAccount();
        BeanUtils.copyProperties(currentBankAccountDTO,currentAccount);
        currentAccount.setCustomer(customerMapper.fromCustomerDtoToCustomer(currentBankAccountDTO.getCustomerDTO()));
        return currentAccount;
    }

    public AccountOperationDTO fromAccountOperationToAccountOperationDTO(AccountOperation accountOperation){
        AccountOperationDTO accountOperationDTO = new AccountOperationDTO();
        BeanUtils.copyProperties(accountOperation, accountOperationDTO);
        return accountOperationDTO ;
    }

}
