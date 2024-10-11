package com.example.mod2.model;

public record Student(Integer id, String firstName, String lastName, Integer age) {
    @Override
    public String toString() {
        return String.format("%d;%s;%s;%d", id, firstName, lastName, age);
    }
}
