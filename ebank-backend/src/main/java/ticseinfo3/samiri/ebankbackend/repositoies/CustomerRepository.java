package ticseinfo3.samiri.ebankbackend.repositoies;

import org.springframework.data.jpa.repository.JpaRepository;
import ticseinfo3.samiri.ebankbackend.entities.Customer;

import java.util.List;

public interface CustomerRepository extends JpaRepository<Customer, Long> {
    List<Customer> findByNameContains(String name);

    List<Customer> findByEmailContains(String Email);
}
