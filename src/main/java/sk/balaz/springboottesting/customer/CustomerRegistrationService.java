package sk.balaz.springboottesting.customer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CustomerRegistrationService {

    private final CustomerRepository customerRepository;

    @Autowired
    public CustomerRegistrationService(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    public  void registerNewCustomer(CustomerRegistrationRequest customerRegistrationRequest) {
        // 1. phone number is taken

        //2. if taken lets check if belongs to same customer

        //2.1 if yes return

        //2.2 throw an exception

        //3. save customer
    }
}
