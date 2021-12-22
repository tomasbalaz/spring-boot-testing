package sk.balaz.springboottesting.payment;

import java.math.BigDecimal;
import java.util.UUID;

public class Payment {

    private Long paymentId;

    private UUID customerId;

    private BigDecimal amount;

    private Currency currency;

    private String source;

    private String description;

    public Payment() {
    }

    public Payment(Long paymentId,
                   UUID customerId,
                   BigDecimal amount,
                   Currency currency,
                   String source,
                   String description) {
        this.paymentId = paymentId;
        this.customerId = customerId;
        this.amount = amount;
        this.currency = currency;
        this.source = source;
        this.description = description;
    }

    @Override
    public String toString() {
        return "Payment{" +
                "paymentId=" + paymentId +
                ", customerId=" + customerId +
                ", amount=" + amount +
                ", currency=" + currency +
                ", source='" + source + '\'' +
                ", description='" + description + '\'' +
                '}';
    }
}
