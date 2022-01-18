package sk.balaz.springboottesting.utils;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
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
         String phoneNumber = "+447000000000";
        //when
        boolean isValid = underTest.test(phoneNumber);

        //then
        assertThat(isValid).isTrue();
    }

    @Test
    @DisplayName("Should fail when length is bigger than 13")
    void itShouldValidatePhoneNumberWhenIncorrectAndHasLengthBiggerThan13() {
        //given
        String phoneNumber = "+447000000000884848";
        //when
        boolean isValid = underTest.test(phoneNumber);

        //then
        assertThat(isValid).isFalse();
    }

    @Test
    @DisplayName("Should fail when it does not start with +")
    void itShouldValidatePhoneNumberWhenDoesNotStartWithPlusSign() {
        //given
        String phoneNumber = "447000000000884848";
        //when
        boolean isValid = underTest.test(phoneNumber);

        //then
        assertThat(isValid).isFalse();
    }
}
