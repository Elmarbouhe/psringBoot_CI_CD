package ticseinfo3.samiri.ebankbackend.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Customer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private  Long id ;
    private String name;
    private String email ;

    @OneToMany(mappedBy = "customer")  // mabedBy ppour evety la criation d'une table qui garde 2 cly itronger (la rolation beDirectionel)
    private List<BankAccount> bankAccounts;

}
