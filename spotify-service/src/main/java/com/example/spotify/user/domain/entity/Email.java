package com.example.spotify.user.domain.entity;

import jakarta.ws.rs.NotAcceptableException;

import java.util.Objects;
import java.util.regex.Pattern;

public class Email {
    private static final Pattern EMAIL_PATTERN =
            Pattern.compile("^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$");

    private final String value;

    public Email(String value) {
        this.value = value;
    }

    public static Email of(String email) {
        if (email == null || email.isEmpty()) {
            throw new NotAcceptableException("Email não pode ser vazio");
        }

        if (!EMAIL_PATTERN.matcher(email).matches()) {
            throw new NotAcceptableException("Formato de email inválido");
        }

        return new Email(email);
    }

    public String getValue() {
        return value;
    }

    public boolean isValid() {
        return EMAIL_PATTERN.matcher(value).matches();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Email email = (Email) o;
        return Objects.equals(value.toLowerCase(), email.value.toLowerCase());
    }

    @Override
    public int hashCode() {
        return Objects.hash(value.toLowerCase());
    }

    @Override
    public String toString() {
        return value;
    }
}
