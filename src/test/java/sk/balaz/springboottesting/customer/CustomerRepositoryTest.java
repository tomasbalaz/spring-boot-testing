package sk.balaz.springboottesting.customer;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.dao.DataIntegrityViolationException;

import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

//https://assertj.github.io/doc/
@DataJpaTest(
        properties = "spring.jpa.properties.javax.persistence.validation.mode=none"
)
class CustomerRepositoryTest {

    @Autowired
    private CustomerRepository underTest;

    @Test
    void itShouldSelectCustomerByPhoneNumber() {

        //given - set up of something
        UUID id = UUID.randomUUID();
        String phoneNumber = "00000";
        Customer customer = new Customer(id, "Abel", phoneNumber);

        //when - when something is called
        underTest.save(customer);

        //then - assertion
        Optional<Customer> optionalCustomer = underTest.selectCustomerByPhoneNumber(phoneNumber);
        assertThat(optionalCustomer)
                .isPresent()
                .hasValueSatisfying(c -> {
//                    assertThat(c.getId()).isEqualTo(id);
//                    assertThat(c.getName()).isEqualTo("Abel");
//                    assertThat(c.getPhoneNumber()).isEqualTo("00");

                    assertThat(c).isEqualToComparingFieldByField(customer);
                });
    }

    @Test
    void itShouldNotSelectCustomerByPhoneNumberWhenNumberDoesNotExist() {
        //given - set up of something
        String phoneNumber = "00000";

        //when - when something is called
        Optional<Customer> optionalCustomer = underTest.selectCustomerByPhoneNumber(phoneNumber);

        //then - assertion
        assertThat(optionalCustomer)
                .isNotPresent();
    }

    @Test
    void itShouldSaveCustomer() {

        //given - set up of something
        UUID id = UUID.randomUUID();
        Customer customer = new Customer(id, "Abel", "00000");

        //when - when something is called
        underTest.save(customer);

        //then - assertion
        Optional<Customer> optionalCustomer = underTest.findById(id);
        assertThat(optionalCustomer)
                .isPresent()
                .hasValueSatisfying(c -> {
//                    assertThat(c.getId()).isEqualTo(id);
//                    assertThat(c.getName()).isEqualTo("Abel");
//                    assertThat(c.getPhoneNumber()).isEqualTo("00");

                    assertThat(c).isEqualToComparingFieldByField(customer);
                });
    }

    @Test
    void itShouldNotSaveCustomerWhenNameIsNull() {
        //given - set up of something
        UUID id = UUID.randomUUID();
        Customer customer = new Customer(id, null, "00000");

        //when - when something is called
        //underTest.save(customer);

        //then - assertion
        assertThatThrownBy(() -> underTest.save(customer))
                .hasMessageContaining("not-null property references a null or transient value : sk.balaz.springboottesting.customer.Customer.name")
                .isInstanceOf(DataIntegrityViolationException.class);
        //assertThat(underTest.findById(id)).isNotPresent();
    }

    @Test
    void itShouldNotSaveCustomerWhenPhoneNumberIsNull() {

        //given - set up of something
        UUID id = UUID.randomUUID();
        Customer customer = new Customer(id, "tester", null);

        //when - when something is called
        //then - assertion

        assertThatThrownBy(() -> underTest.save(customer))
                .hasMessageContaining("not-null property references a null or transient value : sk.balaz.springboottesting.customer.Customer.phone")
                .isInstanceOf(DataIntegrityViolationException.class);
    }
}