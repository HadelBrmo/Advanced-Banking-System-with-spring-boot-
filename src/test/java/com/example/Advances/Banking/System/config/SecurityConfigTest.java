package com.example.Advances.Banking.System.config;

import org.junit.jupiter.api.Test;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import static org.junit.jupiter.api.Assertions.*;


class SimpleSecurityTest {

    @Test
    void testBCryptPasswordEncoder() {
        PasswordEncoder encoder = new BCryptPasswordEncoder();

        String password = "testPassword";
        String encoded = encoder.encode(password);

        System.out.println("🔐 اختبار تشفير كلمة المرور:");
        System.out.println("   كلمة المرور الأصلية: " + password);
        System.out.println("   كلمة المرور المشفرة: " + encoded);

        assertTrue(encoder.matches(password, encoded));
        System.out.println("✅ التطابق صحيح");

        String encoded2 = encoder.encode(password);
        assertNotEquals(encoded, encoded2);
        System.out.println("✅ نفس الباسورد يعطي تشفير مختلف (بسبب Salt)");
    }
}