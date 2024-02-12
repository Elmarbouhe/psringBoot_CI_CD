package ticseinfo3.samiri.ebankbackend.controllers;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ticseinfo3.samiri.ebankbackend.dto.*;
import ticseinfo3.samiri.ebankbackend.exeptions.BankAccountNotFondException;
import ticseinfo3.samiri.ebankbackend.exeptions.CustomerNotFundException;
import ticseinfo3.samiri.ebankbackend.services.BankAccountService;

import java.util.List;

@RestController
@AllArgsConstructor
@Slf4j
@RequestMapping("/accounts")
@CrossOrigin("*")
public class BankAccountController {

    private BankAccountService bankAccountService;

    @PostMapping("/create/current/{initialBalance}/{overDraft}/{customerId}")
    public CurrentBankAccountDTO addCurrentBankAccount(@PathVariable double initialBalance,
                                                       @PathVariable double overDraft,
                                                       @PathVariable Long customerId) throws CustomerNotFundException {
        return bankAccountService.addCurrentBankAccount(initialBalance,overDraft,customerId);
    }

    @PostMapping("/create/saving/{initialBalance}/{intrestRate}/{customerId}")
    public SavingBankAccountDTO addSavingBankAccount(@PathVariable double initialBalance,
                                                     @PathVariable double intrestRate,
                                                     @PathVariable Long customerId) throws CustomerNotFundException {
        return bankAccountService.addSavingBankAccount(initialBalance,intrestRate,customerId);
    }

    @DeleteMapping("/delete/{accountId}")
    public String deleteBankAccount(@PathVariable String accountId) throws BankAccountNotFondException {
        return bankAccountService.deleteBankAccount(accountId);
    }
    @GetMapping("/{accountId}")
    public BankAccountDTO getBankAccount(@PathVariable String accountId) throws BankAccountNotFondException {
        return bankAccountService.getBankAccount(accountId);
    }

    // get all bank accounts
    @GetMapping("/Liste")
    public List<BankAccountDTO> getBankAccounts(){
        return bankAccountService.getAllBankAccounts();
    }

}
