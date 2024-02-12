package ticseinfo3.samiri.ebankbackend.mappers;

import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import ticseinfo3.samiri.ebankbackend.dto.CustomerDTO;
import ticseinfo3.samiri.ebankbackend.entities.Customer;
@Service
public class CustomerMapperImpl {
    public CustomerDTO fromCustomerToCustomerDTO(Customer customer){
        CustomerDTO customerDTO = new CustomerDTO();
        BeanUtils.copyProperties(customer,customerDTO);
        return customerDTO;
    }

    public Customer fromCustomerDtoToCustomer(CustomerDTO customerDTO){
        Customer customer= new Customer();
        BeanUtils.copyProperties(customerDTO,customer);
        return customer;
    }


}
