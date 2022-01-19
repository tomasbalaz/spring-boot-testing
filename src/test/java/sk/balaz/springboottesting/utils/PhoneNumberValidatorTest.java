package sk.balaz.springboottesting.utils;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.assertj.core.api.Assertions.assertThat;

public class PhoneNumberValidatorTest {

    private PhoneNumberValidator underTest;

    @BeforeEach
    void setUp() {
        underTest = new PhoneNumberValidator();
    }

    @ParameterizedTest()
    @CsvSource({
            "+447000000000,true",
            "+44700000000084848,false",
            "44700000000084848,false"
    })
    void itShouldValidatePhoneNumber(String phoneNumber, boolean expected) {
        //given
        //when
        boolean isValid = underTest.test(phoneNumber);

        //then
        assertThat(isValid).isEqualTo(expected);
    }
}
