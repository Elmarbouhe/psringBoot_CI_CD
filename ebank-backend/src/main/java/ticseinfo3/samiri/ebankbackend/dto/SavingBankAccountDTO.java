package ticseinfo3.samiri.ebankbackend.dto;


import lombok.Data;
import ticseinfo3.samiri.ebankbackend.enums.AccountStatus;
import java.util.Date;

@Data
public class SavingBankAccountDTO extends BankAccountDTO {

    private String id ;
    private double balance ;
    private Date createdAt ;
    private AccountStatus status ;
    private CustomerDTO customerDTO ;
    private  double interestRate;
}
