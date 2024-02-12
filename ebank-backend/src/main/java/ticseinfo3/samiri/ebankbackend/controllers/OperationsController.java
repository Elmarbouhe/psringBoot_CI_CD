package ticseinfo3.samiri.ebankbackend.controllers;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ticseinfo3.samiri.ebankbackend.dto.*;
import ticseinfo3.samiri.ebankbackend.exeptions.BankAccountNotFondException;
import ticseinfo3.samiri.ebankbackend.exeptions.BanlnceNotSufacientException;
import ticseinfo3.samiri.ebankbackend.services.AccountOperationsService;


import java.util.List;

@RestController
@AllArgsConstructor
@Slf4j
@RequestMapping("/operations")
@CrossOrigin("*")
public class OperationsController {

    private AccountOperationsService accountOperationsService;

    @PostMapping("/debit")
    public DebitDTO debit(@RequestBody DebitDTO debit) throws BankAccountNotFondException, BanlnceNotSufacientException{
        accountOperationsService.debit(debit.getAccountId(),debit.getAmount(),debit.getDescription());
        return debit ;
    }

    @PostMapping("/credit")
    public CreditDTO credit(@RequestBody CreditDTO credit) throws BankAccountNotFondException,   BanlnceNotSufacientException{
        accountOperationsService.credit(credit.getAccountId(),credit.getAmount(),credit.getDescription());
        return credit ;
    }

    @PostMapping("/transfer")
    public void transfer(@RequestBody TransferRequestDTO transfer) throws BankAccountNotFondException, BanlnceNotSufacientException{
        accountOperationsService.transfer(transfer.getAccountSource(),transfer.getAccountDestination(),transfer.getAmount(),transfer.getDescription());   ;
    }



    @GetMapping("/operations/{accountId}")
    public List<AccountOperationDTO> accountHistory(@PathVariable String accountId){
        return accountOperationsService.accountHistory(accountId);
    }

    @GetMapping("/accountHistory/{accountId}")
    public AccountHistoryDTO getAccountHistory(@PathVariable String accountId,
                                               @RequestParam(name = "page" ,defaultValue = "0") int page,
                                               @RequestParam(name = "size" ,defaultValue = "5") int size) throws BankAccountNotFondException {
        return accountOperationsService.getAccountHistory(accountId,page,size);
    }

}
