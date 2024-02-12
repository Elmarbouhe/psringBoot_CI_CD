package ticseinfo3.samiri.ebankbackend.services;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ticseinfo3.samiri.ebankbackend.dto.CustomerDTO;
import ticseinfo3.samiri.ebankbackend.entities.Customer;
import ticseinfo3.samiri.ebankbackend.exeptions.CustomerNotFundException;
import ticseinfo3.samiri.ebankbackend.mappers.CustomerMapperImpl;
import ticseinfo3.samiri.ebankbackend.repositoies.CustomerRepository;

import java.util.List;
import java.util.stream.Collectors;

@Transactional
@Service
@AllArgsConstructor
@Slf4j
public class CustomerServiceImpl implements CustomerService {

    private CustomerMapperImpl dtoMapper;
    private CustomerRepository customerRepo;

    @Override
    public CustomerDTO addCustomer(CustomerDTO customerDTO) {
        log.info("Adding new customer : {}");
        Customer customer = dtoMapper.fromCustomerDtoToCustomer(customerDTO);
        Customer addedCustomer = customerRepo.save(customer);
        return dtoMapper.fromCustomerToCustomerDTO(addedCustomer);
    }

    @Override
    public  CustomerDTO updaCustomer(CustomerDTO customerDTO){
        log.info("Update the Customer " +customerDTO.getName() );
        Customer customer = dtoMapper.fromCustomerDtoToCustomer(customerDTO);
        Customer addedCustomer = customerRepo.save(customer);
        return dtoMapper.fromCustomerToCustomerDTO(addedCustomer);
    }

    @Override
    public void deletCustomer(long id) {
        customerRepo.deleteById(id);
    }

    @Override
    public CustomerDTO getCustomer(Long id) throws CustomerNotFundException {
        Customer customer = customerRepo.findById(id)
                .orElseThrow(()->new CustomerNotFundException("customer not fond"));

        return dtoMapper.fromCustomerToCustomerDTO(customer);
    }

    @Override
    public List<CustomerDTO> getAllCustomers() {
        List<Customer> customers = customerRepo.findAll();
        List<CustomerDTO> customerDTOS =  customers.stream()
                .map(cust->dtoMapper.fromCustomerToCustomerDTO(cust)).collect(Collectors.toList());
        return customerDTOS;
    }

    @Override
    public List<CustomerDTO> searchCustomersByName(String key) {
        List<Customer> customers = customerRepo.findByNameContains(key);
        List<CustomerDTO> customerDTOS = customers.stream().map(cus-> dtoMapper.fromCustomerToCustomerDTO(cus)).collect(Collectors.toList());
        return customerDTOS ;
    }

    @Override
    public List<CustomerDTO> searchCustomersByEmail(String email) {
        List<Customer> customers = customerRepo.findByEmailContains(email);
        List<CustomerDTO> customerDTOS = customers.stream()
                .map(customer -> dtoMapper.fromCustomerToCustomerDTO(customer)).collect(Collectors.toList());
        return customerDTOS;
    }
}
