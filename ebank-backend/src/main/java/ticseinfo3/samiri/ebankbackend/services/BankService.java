package ticseinfo3.samiri.ebankbackend.services;

import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ticseinfo3.samiri.ebankbackend.entities.BankAccount;
import ticseinfo3.samiri.ebankbackend.entities.CurrentAccount;
import ticseinfo3.samiri.ebankbackend.entities.SavingAccount;
import ticseinfo3.samiri.ebankbackend.repositoies.BankAccountRepository;

@Service
@Transactional
public class BankService {

    @Autowired
    private BankAccountRepository bankAccountRepository ;
    public void cunsult(){
        BankAccount account = bankAccountRepository.findById("04c0a356-832f-4b0c-8049-ac67f9252474").get();
        if (account!=null){
            System.out.println("**************************************************");
            System.out.println(account.getId());
            System.out.println(account.getBalance());
            System.out.println(account.getStatus());
            System.out.println(account.getCreatedAt());
            System.out.println(account.getCustomer().getName());
            if (account instanceof CurrentAccount){
                System.out.println ("Over draft=>"+((CurrentAccount)account).getOverDraft());
            } else if (account instanceof SavingAccount) {
                System.out.println("tau D'intere=>"+((SavingAccount)account).getIntrestRate());
            }
            account.getAccountOperations().forEach(op->{
                System.out.println(op.getType()+"\t"+op.getOperationDate()+"\t"+op.getAmount());
            });
        }
    }
}
