package ticseinfo3.samiri.ebankbackend.repositoies;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ticseinfo3.samiri.ebankbackend.entities.BankAccount;
@Repository

public interface BankAccountRepository extends JpaRepository<BankAccount,String> {
}
