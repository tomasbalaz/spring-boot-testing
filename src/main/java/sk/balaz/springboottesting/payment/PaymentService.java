package sk.balaz.springboottesting.payment;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import sk.balaz.springboottesting.customer.Customer;
import sk.balaz.springboottesting.customer.CustomerRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class PaymentService {

    private static final List<Currency> ACCEPTED_CURRENCIES = List.of(Currency.USD, Currency.GBP);

    private final CustomerRepository customerRepository;

    private final PaymentRepository paymentRepository;

    private final  CardPaymentCharger cardPaymentCharger;

    @Autowired
    public PaymentService(CustomerRepository customerRepository,
                          PaymentRepository paymentRepository,
                          CardPaymentCharger cardPaymentCharger) {
        this.customerRepository = customerRepository;
        this.paymentRepository = paymentRepository;
        this.cardPaymentCharger = cardPaymentCharger;
    }

    public void charge(UUID customerId, PaymentRequest paymentRequest) {
        //1. Does customer exists if not throw
        //2. Do we support the currency if not throw
        //3. Charge card
        //4. If not debited throw
        //5. Insert payment
        //6. TODO : send sms

        Optional<Customer> optionalCustomer = customerRepository.findById(customerId);
        if(!optionalCustomer.isPresent()) {
            throw new IllegalStateException(String.format("Customer with id [%s] not found", customerId));
        }

        boolean isCurrencySupported = ACCEPTED_CURRENCIES.stream()
                .anyMatch(c -> c.equals(paymentRequest.getPayment().getCurrency()));

        if(!isCurrencySupported) {
            String message = String.format("Currency [%s] is not supported", paymentRequest.getPayment().getCurrency());
            throw new IllegalStateException(message);
        }

        CardPaymentCharge cardPaymentCharge = cardPaymentCharger.chargeCard(
                paymentRequest.getPayment().getSource(),
                paymentRequest.getPayment().getAmount(),
                paymentRequest.getPayment().getCurrency(),
                paymentRequest.getPayment().getDescription()
        );

        if (!cardPaymentCharge.isCarDebited()) {
            throw new IllegalStateException(String.format("Card not debited for customer %s", customerId));
        }

        paymentRequest.getPayment().setCustomerId(customerId);

        paymentRepository.save(paymentRequest.getPayment());
    }
}
