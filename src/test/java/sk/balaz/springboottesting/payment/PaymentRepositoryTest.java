package sk.balaz.springboottesting.payment;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.math.BigDecimal;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest(
        properties = "spring.jpa.properties.javax.persistence.validation.mode=none"
)
class PaymentRepositoryTest {

    @Autowired
    private PaymentRepository underTest;

    @Test
    void shouldInsertPayment() {
        //given
        long paymentId = 1L;

        Payment payment = new Payment(
                paymentId,
                UUID.randomUUID(),
                new BigDecimal("10"),
                Currency.USD,
                "card123",
                "donation"
        );
        //when
        underTest.save(payment);

        //then
        Optional<Payment>paymentIdOptional = underTest.findById(paymentId);
        assertThat(paymentIdOptional)
                .isPresent()
                .hasValueSatisfying(p -> assertThat(p).isEqualTo(payment));
    }
}