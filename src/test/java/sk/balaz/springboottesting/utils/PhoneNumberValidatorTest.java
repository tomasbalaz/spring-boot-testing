package sk.balaz.springboottesting.utils;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class PhoneNumberValidatorTest {

    private PhoneNumberValidator underTest;

    @BeforeEach
    void setUp() {
        underTest = new PhoneNumberValidator();
    }

    @Test
    void itShouldValidatePhoneNumber() {
        //given
         String phoneNumber = "+447000000";
        //when
        boolean isValid = underTest.test(phoneNumber);

        //then
        assertThat(isValid).isTrue();
    }
}
