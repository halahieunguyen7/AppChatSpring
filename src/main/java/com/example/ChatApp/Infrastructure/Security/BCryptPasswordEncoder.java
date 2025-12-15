package com.example.ChatApp.Infrastructure.Security;

import com.example.ChatApp.Domain.Auth.Model.PasswordHash;
import com.example.ChatApp.Domain.Auth.Service.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.security.crypto.bcrypt.BCrypt;

@Component
public class BCryptPasswordEncoder implements PasswordEncoder {

    private static final int DEFAULT_STRENGTH = 12;

    @Override
    public PasswordHash encode(String rawPassword) {
        validateRawPassword(rawPassword);

        String salt = BCrypt.gensalt(DEFAULT_STRENGTH);
        String hashed = BCrypt.hashpw(rawPassword, salt);

        return new PasswordHash(hashed);
    }

    @Override
    public boolean matches(String rawPassword, PasswordHash hash) {
        if (rawPassword == null || hash == null) {
            return false;
        }
        return BCrypt.checkpw(rawPassword, hash.value());
    }

    /**
     * Password policy (application/infrastructure concern)
     */
    private void validateRawPassword(String rawPassword) {
        if (rawPassword == null) {
            throw new IllegalArgumentException("Password must not be null");
        }
        if (rawPassword.length() < 8) {
            throw new IllegalArgumentException(
                    "Password must be at least 8 characters"
            );
        }
    }
}