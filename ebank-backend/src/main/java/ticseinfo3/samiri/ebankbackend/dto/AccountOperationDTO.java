package ticseinfo3.samiri.ebankbackend.dto;

import lombok.Data;
import ticseinfo3.samiri.ebankbackend.enums.OperationType;
import java.util.Date;

@Data
public class AccountOperationDTO {

    private  Long id ;
    private Date operationDate ;
    private double amount ;
    private OperationType type ;
    private String description ;

}


