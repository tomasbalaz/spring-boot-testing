package sk.balaz.springboottesting.payment;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import sk.balaz.springboottesting.customer.CustomerRepository;

import java.util.UUID;

@Service
public class PaymentService {

    private final CustomerRepository customerRepository;

    private final PaymentRepository paymentRepository;

    private final  CardPaymentCharger cardPaymentCharger;

    @Autowired
    public PaymentService(CustomerRepository customerRepository, PaymentRepository paymentRepository, CardPaymentCharger cardPaymentCharger) {
        this.customerRepository = customerRepository;
        this.paymentRepository = paymentRepository;
        this.cardPaymentCharger = cardPaymentCharger;
    }

    public void charge(UUID customerId, PaymentRequest paymentRequest) {

    }
}
