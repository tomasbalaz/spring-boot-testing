package sk.balaz.springboottesting.payment;

public class CardPaymentCharge {

    private final boolean isCarDebited;

    public CardPaymentCharge(boolean isCarDebited) {
        this.isCarDebited = isCarDebited;
    }

    public boolean isCarDebited() {
        return isCarDebited;
    }

    @Override
    public String toString() {
        return "CardPaymentCharge{" +
                "isCardebited=" + isCarDebited +
                '}';
    }
}
