package sk.balaz.springboottesting.payment;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import sk.balaz.springboottesting.customer.Customer;
import sk.balaz.springboottesting.customer.CustomerRegistrationController;

import java.util.UUID;

@SpringBootTest
public class PaymentIntegrationTest {

    // Do not autowire Controllers in Integration test.
    //@Autowired
   //private CustomerRegistrationController customerRegistrationController;

    @Test
    void PaymentIntegrationTest() {
        //given
        UUID customerId = UUID.randomUUID();
        Customer customer = new Customer(customerId, "James", "000000");

        //when
        // In this way REST api POST api/v1/customer-registration is not called, just method is called.
        //customerRegistrationController.registerNewCustomer(customer);

        //then

    }
}
