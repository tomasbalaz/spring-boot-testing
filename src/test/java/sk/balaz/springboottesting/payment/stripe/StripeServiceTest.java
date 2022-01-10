package sk.balaz.springboottesting.payment.stripe;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import sk.balaz.springboottesting.payment.Currency;

import java.math.BigDecimal;

class StripeServiceTest {

    private StripeService underTest;

    @Mock
    private StripeApi stripeApi;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        underTest = new StripeService(stripeApi);
    }

    @Test
    void itShouldChargeCard() {
        //given
        String cardSource = "0x0x0x";
        BigDecimal amount = new BigDecimal("10");
        Currency usd = Currency.USD;
        String donate = "Donate";

        //when
        underTest.chargeCard(cardSource, amount, usd, donate);

        //then

    }
}