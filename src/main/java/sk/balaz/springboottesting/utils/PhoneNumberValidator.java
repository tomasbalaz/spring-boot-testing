package sk.balaz.springboottesting.utils;

import java.util.function.Predicate;

public class PhoneNumberValidator implements Predicate<String> {

    @Override
    public boolean test(String s) {
        return false;
    }
}
