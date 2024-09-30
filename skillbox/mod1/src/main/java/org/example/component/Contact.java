package org.example.component;

public record Contact (String name, String number, String email) {
    @Override
    public String toString() {
        return String.format("%s;%s;%s", name, number, email);
    }
}
