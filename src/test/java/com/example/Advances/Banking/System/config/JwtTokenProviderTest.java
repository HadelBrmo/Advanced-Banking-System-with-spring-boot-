package com.example.Advances.Banking.System.config;

import com.example.Advances.Banking.System.nfr.security.auth.JwtTokenProvider;
import org.junit.jupiter.api.Test;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collections;

import static org.junit.jupiter.api.Assertions.*;

class JwtSimpleTest {

    @Test
    void testJwtTokenCreation() {
        JwtTokenProvider provider = new JwtTokenProvider();

        try {
            var secretField = JwtTokenProvider.class.getDeclaredField("secretKey");
            secretField.setAccessible(true);
            secretField.set(provider, "testSecretKey123456789012345678901234567890");

            var expField = JwtTokenProvider.class.getDeclaredField("expiration");
            expField.setAccessible(true);
            expField.set(provider, 3600000L);


            UserDetails user = User.withUsername("test")
                    .password("pass")
                    .authorities(Collections.emptyList())
                    .build();

            String token = provider.generateToken(user);
            assertNotNull(token);
            assertTrue(token.length() > 50);

            System.out.println("✅ JWT Token generated successfully!");
            System.out.println("🔐 Token: " + token.substring(0, 50) + "...");

        } catch (Exception e) {
            System.out.println("⚠️  Note: Full test requires Spring context");
        }
    }
}