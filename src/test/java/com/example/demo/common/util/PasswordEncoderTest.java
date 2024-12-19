package com.example.demo.common.util;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;

class PasswordEncoderTest {

    @Mock
    private PasswordEncoder passwordEncoder;

    @Test
    @DisplayName("비밀번호 encoding 시 정상적으로 작동하는지 확인")
    void testEncodedPassWord() throws Exception {
        // Given
        String password = "asd123!";
        String encodedPassword = passwordEncoder.encode(password);
        System.out.println(encodedPassword);

        // When

        // Then
    }

    @Test
    @DisplayName("비밀번호 encoding 시 정상적으로 작동하는지 확인")
    void testMatches() {
        // Given
        String password = "asd123!";
        String encodedPassword = passwordEncoder.encode(password);
       if (passwordEncoder.matches(password, encodedPassword)) {
           System.out.println("true");
        } else {
           System.out.println("false");
       }


        // When

        // Then
    }
}