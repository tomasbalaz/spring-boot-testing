package sk.balaz.springboottesting.payment;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import sk.balaz.springboottesting.customer.Customer;
import sk.balaz.springboottesting.customer.CustomerRegistrationRequest;

import java.util.Objects;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.fail;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class PaymentIntegrationTest {

    // Do not autowire Controllers in Integration test.
    //@Autowired
   //private CustomerRegistrationController customerRegistrationController;

    @Autowired
    private MockMvc mockMvc;

    @Test
    void itShouldCreatePaymentSuccessfully() throws Exception {
        //given
        UUID customerId = UUID.randomUUID();
        Customer customer = new Customer(customerId, "James", "000000");

        CustomerRegistrationRequest customerRegistrationRequest = new CustomerRegistrationRequest(customer);

        // In this way REST api POST api/v1/customer-registration is not called, just method is called.
        //customerRegistrationController.registerNewCustomer(customer);

        ResultActions customerRegResultActions = mockMvc.perform(put("/api/v1/customer-registration")
                .contentType(MediaType.APPLICATION_JSON)
                .content(Objects.requireNonNull(asJsonString(customerRegistrationRequest))));

        System.out.println(customerRegResultActions);
        //when


        //then
        customerRegResultActions.andExpect(status().isOk());
    }

    private String asJsonString( Object object) {
        try {
            return new ObjectMapper().writeValueAsString(object);
        } catch (JsonProcessingException e) {
            fail("Failed convert object to json");
            return null;
        }
    }
}
