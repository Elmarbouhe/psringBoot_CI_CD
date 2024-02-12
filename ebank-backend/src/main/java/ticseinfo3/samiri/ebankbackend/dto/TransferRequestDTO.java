package ticseinfo3.samiri.ebankbackend.dto;

import lombok.Data;

@Data
public class TransferRequestDTO {
    private String accountSource;
    private String accountDestination;
    private Double amount;
    private String description;
}
