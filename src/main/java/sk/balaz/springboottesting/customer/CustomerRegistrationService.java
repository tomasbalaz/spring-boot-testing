package sk.balaz.springboottesting.customer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import sk.balaz.springboottesting.utils.PhoneNumberValidator;

import java.util.Optional;
import java.util.UUID;

@Service
public class CustomerRegistrationService {

    private final CustomerRepository customerRepository;

    private PhoneNumberValidator phoneNumberValidator;

    @Autowired
    public CustomerRegistrationService(CustomerRepository customerRepository,
                                       PhoneNumberValidator phoneNumberValidator) {
        this.customerRepository = customerRepository;
        this.phoneNumberValidator = phoneNumberValidator;
    }

    public  void registerNewCustomer(CustomerRegistrationRequest request) {
        // 1. phone number is taken

        //2. if taken lets check if belongs to same customer

        //2.1 if yes return

        //2.2 throw an exception

        //3. save customer

        String phoneNumber = request.getCustomer().getPhoneNumber();

        //TODO: validate that phone number is valid
        if(!phoneNumberValidator.test(phoneNumber)) {
            throw new IllegalStateException("Phone number "+ phoneNumber + " is not valid");
        }

        Optional<Customer> customerOptional = customerRepository.selectCustomerByPhoneNumber(phoneNumber);

        if (customerOptional.isPresent()) {
            Customer customer = customerOptional.get();
            if (customer.getName().equals(request.getCustomer().getName())) {
                return;
            }
            throw new IllegalStateException(String.format("phone number [%s] is taken", phoneNumber));
        }

        if(request.getCustomer().getId() == null) {
            request.getCustomer().setId(UUID.randomUUID());
        }

        customerRepository.save(request.getCustomer());

    }
}
