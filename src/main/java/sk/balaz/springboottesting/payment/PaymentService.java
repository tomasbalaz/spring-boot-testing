package sk.balaz.springboottesting.payment;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import sk.balaz.springboottesting.customer.CustomerRepository;

import java.util.UUID;

@Service
public class PaymentService {

    private final CustomerRepository customerRepository;

    private final PaymentRepository paymentRepository;

    @Autowired
    public PaymentService(CustomerRepository customerRepository, PaymentRepository paymentRepository) {
        this.customerRepository = customerRepository;
        this.paymentRepository = paymentRepository;
    }

    public void charge(UUID customerId, PaymentRequest paymentRequest) {

    }
}
