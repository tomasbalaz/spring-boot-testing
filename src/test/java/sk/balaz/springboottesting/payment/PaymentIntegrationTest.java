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

import java.math.BigDecimal;
import java.util.Objects;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
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

    @Autowired
    private PaymentRepository paymentRepository;

    @Test
    void itShouldCreatePaymentSuccessfully() throws Exception {
        //given a customer
        UUID customerId = UUID.randomUUID();
        Customer customer = new Customer(customerId, "James", "000000");

        CustomerRegistrationRequest customerRegistrationRequest = new CustomerRegistrationRequest(customer);

        // In this way REST api POST api/v1/customer-registration is not called, just method is called.
        //customerRegistrationController.registerNewCustomer(customer);

        // ... Register request
        ResultActions customerRegResultActions = mockMvc.perform(put("/api/v1/customer-registration")
                .contentType(MediaType.APPLICATION_JSON)
                .content(Objects.requireNonNull(asJsonString(customerRegistrationRequest))));

        System.out.println(customerRegResultActions);

        // ... Payment
        long paymentId = 1L;
        Payment payment = new Payment(paymentId,
                customerId,
                new BigDecimal("100.00"),
                Currency.GBP,
                "x0x0x0",
                "donation"
        );

        // ... when payment is sent
        PaymentRequest paymentRequest = new PaymentRequest(payment);

        //when
        // then both customer registration and payment requests are 200 status code
        ResultActions paymentResultActions = mockMvc.perform(put("/api/v1/payment/{customerId}", customerId)
                .contentType(MediaType.APPLICATION_JSON)
                .content(Objects.requireNonNull(asJsonString(paymentRequest)))
        );

        //then
        customerRegResultActions.andExpect(status().isOk());
        paymentResultActions.andExpect(status().isOk());

        // Payment is stored in db
        // TODO: Do not use paymentRepository instead create an endpoint to retrieve payments for customers
        assertThat(paymentRepository.findById(paymentId))
                .isPresent()
                .hasValueSatisfying(p -> assertThat(p).isEqualToIgnoringGivenFields(payment));

        // TODO: Ensure sms is delivered
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
