package com.example.Advances.Banking.System.controller;

import com.example.Advances.Banking.System.nfr.security.auth.JwtTokenProvider;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
@Slf4j
public class AuthenticationController {

    private final AuthenticationManager authenticationManager;
    private final JwtTokenProvider jwtTokenProvider;
    private final UserDetailsService userDetailsService;

    @PostMapping("/login")
    public ResponseEntity<Map<String, String>> login(@RequestBody LoginRequest request) {
        log.info("🔐 محاولة تسجيل دخول للمستخدم: {}", request.getUsername());

        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getUsername(),
                        request.getPassword()
                )
        );


        UserDetails userDetails = userDetailsService.loadUserByUsername(request.getUsername());

       //التوكن
        String token = jwtTokenProvider.generateToken(userDetails);

        // 4. إعداد الرد
        Map<String, String> response = new HashMap<>();
        response.put("token", token);
        response.put("username", userDetails.getUsername());
        response.put("message", "✅ تسجيل الدخول ناجح");
        response.put("status", "SUCCESS");

        log.info("✅ تسجيل دخول ناجح للمستخدم: {}", request.getUsername());

        return ResponseEntity.ok(response);
    }

    /**
     * التحقق من صحة token
     *
     * POST /api/auth/validate
     *
     * Request Header:
     * Authorization: Bearer {token}
     *
     * Response:
     * {
     *   "valid": true,
     *   "username": "admin",
     *   "message": "Token is valid"
     * }
     */
    @PostMapping("/validate")
    public ResponseEntity<Map<String, Object>> validateToken(
            @RequestHeader("Authorization") String authHeader) {

        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            return ResponseEntity.badRequest().body(Map.of(
                    "valid", false,
                    "message", "❌ Authorization header is missing or invalid"
            ));
        }

        String token = authHeader.substring(7);
        String username = jwtTokenProvider.extractUsername(token);

        if (username != null) {
            UserDetails userDetails = userDetailsService.loadUserByUsername(username);
            boolean isValid = jwtTokenProvider.validateToken(token, userDetails);

            if (isValid) {
                return ResponseEntity.ok(Map.of(
                        "valid", true,
                        "username", username,
                        "message", "✅ Token is valid"
                ));
            }
        }

        return ResponseEntity.ok(Map.of(
                "valid", false,
                "message", "❌ Token is invalid or expired"
        ));
    }

    /**
     * تجديد token
     *
     * POST /api/auth/refresh
     */
    @PostMapping("/refresh")
    public ResponseEntity<Map<String, String>> refreshToken(
            @RequestHeader("Authorization") String authHeader) {

        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            return ResponseEntity.badRequest().body(Map.of(
                    "error", "Invalid authorization header"
            ));
        }

        String oldToken = authHeader.substring(7);
        String username = jwtTokenProvider.extractUsername(oldToken);

        if (username != null) {
            UserDetails userDetails = userDetailsService.loadUserByUsername(username);
            String newToken = jwtTokenProvider.generateToken(userDetails);

            return ResponseEntity.ok(Map.of(
                    "token", newToken,
                    "username", username,
                    "message", "✅ Token refreshed successfully"
            ));
        }

        return ResponseEntity.badRequest().body(Map.of(
                "error", "Cannot refresh invalid token"
        ));
    }

    /**
     * Health check للـ authentication service
     *
     * GET /api/auth/health
     */
    @GetMapping("/health")
    public ResponseEntity<Map<String, String>> healthCheck() {
        return ResponseEntity.ok(Map.of(
                "status", "UP",
                "service", "Authentication Service",
                "timestamp", String.valueOf(System.currentTimeMillis()),
                "message", "🔐 خدمة المصادقة تعمل بشكل طبيعي"
        ));
    }

    /**
     * نموذج طلب تسجيل الدخول (Data Transfer Object)
     */
    public static class LoginRequest {
        private String username;
        private String password;


        public String getUsername() { return username; }
        public void setUsername(String username) { this.username = username; }

        public String getPassword() { return password; }
        public void setPassword(String password) { this.password = password; }
    }
}