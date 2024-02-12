package ticseinfo3.samiri.ebankbackend.dto;

import lombok.Data;

@Data
public class DebitDTO {
    private String accountId;
    private Double amount;
    private String description;
}
