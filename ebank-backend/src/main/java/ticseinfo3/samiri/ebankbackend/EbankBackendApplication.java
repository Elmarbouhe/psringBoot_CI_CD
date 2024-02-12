package ticseinfo3.samiri.ebankbackend;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import ticseinfo3.samiri.ebankbackend.entities.*;
import ticseinfo3.samiri.ebankbackend.enums.AccountStatus;
import ticseinfo3.samiri.ebankbackend.enums.OperationType;
import ticseinfo3.samiri.ebankbackend.exeptions.BankAccountNotFondException;
import ticseinfo3.samiri.ebankbackend.exeptions.BanlnceNotSufacientException;
import ticseinfo3.samiri.ebankbackend.exeptions.CustomerNotFundException;
import ticseinfo3.samiri.ebankbackend.repositoies.AccountOperationRepository;
import ticseinfo3.samiri.ebankbackend.repositoies.BankAccountRepository;
import ticseinfo3.samiri.ebankbackend.repositoies.CustomerRepository;
import ticseinfo3.samiri.ebankbackend.services.BankAccountService;
import ticseinfo3.samiri.ebankbackend.services.BankService;

import java.util.Date;

import java.util.List;
import java.util.UUID;
import java.util.stream.Stream;

@SpringBootApplication
public class    EbankBackendApplication {

    public static void main(String[] args) {
        SpringApplication.run(EbankBackendApplication.class, args);
    }
}


