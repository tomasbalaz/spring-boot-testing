package sk.balaz.springboottesting.payment.stripe;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import sk.balaz.springboottesting.payment.Currency;

import java.math.BigDecimal;

class StripeServiceTest {

    private StripeService underTest;

    @BeforeEach
    void setUp() {
        underTest = new StripeService();
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