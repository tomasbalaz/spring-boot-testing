package sk.balaz.springboottesting.customer;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import sk.balaz.springboottesting.utils.PhoneNumberValidator;

import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.never;

class CustomerRegistrationServiceTest {

   @Mock
   private CustomerRepository customerRepository;
   //private CustomerRepository customerRepository = mock(CustomerRepository.class);

    @Mock
    private PhoneNumberValidator phoneNumberValidator;

    @Captor
    ArgumentCaptor<Customer> customerArgumentCaptor;

    private CustomerRegistrationService underTest;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        underTest = new CustomerRegistrationService(customerRepository, phoneNumberValidator);
    }

    @Test
    void itShouldSaveNewCustomer() {
        //given a phone number and customer
        String phoneNumber = "00099";
        Customer customer = new Customer(UUID.randomUUID(), "Abel", phoneNumber);

        // ... a request
        CustomerRegistrationRequest request = new CustomerRegistrationRequest(customer);

        // ... no customer with phone number passed
        given(customerRepository.selectCustomerByPhoneNumber(phoneNumber))
                .willReturn(Optional.empty());

        // ... valid phone number
        given(phoneNumberValidator.test(phoneNumber)).willReturn(true);

        //when
        underTest.registerNewCustomer(request);

        //then
        then(customerRepository).should().save(customerArgumentCaptor.capture());
        Customer customerArgumentCaptorValue = customerArgumentCaptor.getValue();
        assertThat(customerArgumentCaptorValue).isEqualTo(customer);
    }

    @Test
    void itShouldNotSaveNewCustomerWhenPhoneNumberIsInvalid() {
        //given a phone number and customer
        String phoneNumber = "00099";
        Customer customer = new Customer(UUID.randomUUID(), "Abel", phoneNumber);

        // ... a request
        CustomerRegistrationRequest request = new CustomerRegistrationRequest(customer);

        // ... valid phone number
        given(phoneNumberValidator.test(phoneNumber)).willReturn(false);

        //when
        assertThatThrownBy(() -> {
            underTest.registerNewCustomer(request);
        })
                .isInstanceOf(IllegalStateException.class)
                .hasMessageContaining(String.format("Phone number %s is not valid", phoneNumber));


        //then
        then(customerRepository).shouldHaveNoInteractions();
    }

    @Test
    void itShouldSaveNewCustomerWhenIdIsNull() {
        //given a phone number and customer
        String phoneNumber = "00099";
        Customer customer = new Customer(null, "Abel", phoneNumber);

        // ... a request
        CustomerRegistrationRequest request = new CustomerRegistrationRequest(customer);

        // ... no customer with phone number passed
        given(customerRepository.selectCustomerByPhoneNumber(phoneNumber))
                .willReturn(Optional.empty());

        // ... valid phone number
        given(phoneNumberValidator.test(phoneNumber)).willReturn(true);

        //when
        underTest.registerNewCustomer(request);

        //then
        then(customerRepository).should().save(customerArgumentCaptor.capture());
        Customer customerArgumentCaptorValue = customerArgumentCaptor.getValue();
        assertThat(customerArgumentCaptorValue)
                .isEqualToComparingOnlyGivenFields("id");
        assertThat(customerArgumentCaptorValue.getId()).isNotNull();
    }

    @Test
    void itShouldNotSaveCustomerWhenCustomerExists() {
        //given a phone number and customer
        String phoneNumber = "00099";
        UUID id = UUID.randomUUID();
        Customer customer = new Customer(id, "Abel", phoneNumber);

        // ... a request
        CustomerRegistrationRequest request = new CustomerRegistrationRequest(customer);

        // ... an existing customer is returned
        given(customerRepository.selectCustomerByPhoneNumber(phoneNumber))
                .willReturn(Optional.of(customer));

        // ... valid phone number
        given(phoneNumberValidator.test(phoneNumber)).willReturn(true);

        //when
        underTest.registerNewCustomer(request);


         //1. option
        // then
        // (customerRepository).should(never()).save(any());

        //2. option
        then(customerRepository).should().selectCustomerByPhoneNumber(phoneNumber);
        then(customerRepository).shouldHaveNoMoreInteractions();
    }

    @Test
    void itShouldThrowWhenPhoneNumberIsTaken() {
        //given a phone number and customer
        String phoneNumber = "00099";
        UUID id = UUID.randomUUID();
        Customer customer = new Customer(id, "Abel", phoneNumber);
        Customer customerTwo = new Customer(id, "John", phoneNumber);

        // ... a request
        CustomerRegistrationRequest request = new CustomerRegistrationRequest(customer);

        // ... no customer with phone number passed
        given(customerRepository.selectCustomerByPhoneNumber(phoneNumber))
                .willReturn(Optional.of(customerTwo));

        // ... valid phone number
        given(phoneNumberValidator.test(phoneNumber)).willReturn(true);

        //when

        //then
        assertThatThrownBy(() -> underTest.registerNewCustomer(request))
                .isInstanceOf(IllegalStateException.class)
                .hasMessageContaining(String.format("phone number [%s] is taken", phoneNumber));

        then(customerRepository).should(never()).save(any(Customer.class));
    }
}