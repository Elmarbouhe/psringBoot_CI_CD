package ticseinfo3.samiri.ebankbackend.entities;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;
import ticseinfo3.samiri.ebankbackend.enums.OperationType;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Component
public class AccountOperation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private  Long id ;
    private Date operationDate ;
    private double amount ;
    
    @Enumerated(EnumType.STRING)
    private OperationType type ;

    @ManyToOne
     private BankAccount bankAccount ;

    private String description ;
}
