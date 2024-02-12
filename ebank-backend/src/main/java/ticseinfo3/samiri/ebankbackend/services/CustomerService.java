package ticseinfo3.samiri.ebankbackend.services;

import ticseinfo3.samiri.ebankbackend.dto.CustomerDTO;
import ticseinfo3.samiri.ebankbackend.exeptions.CustomerNotFundException;

import java.util.List;

public interface CustomerService {
    CustomerDTO addCustomer(CustomerDTO customer);
    CustomerDTO updaCustomer(CustomerDTO customerDTO);
    void deletCustomer(long id);
    CustomerDTO getCustomer(Long id) throws CustomerNotFundException;
    List<CustomerDTO> getAllCustomers();
    List<CustomerDTO> searchCustomersByName(String name);
    List<CustomerDTO> searchCustomersByEmail(String email);
}
