package sk.balaz.springboottesting.customer;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

//https://assertj.github.io/doc/
@DataJpaTest
class CustomerRepositoryTest {

    @Autowired
    private CustomerRepository underTest;

    @Test
    void itShouldSelectCustomerByPhoneNumber() {

        //given - set up of something

        //when - when something is called

        //then - assertion
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
}