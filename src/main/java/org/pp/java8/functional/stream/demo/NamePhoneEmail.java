package org.pp.java8.functional.stream.demo;

import java.util.Objects;

class NamePhoneEmail {
    String name;
    String phone;
    String email;

    public NamePhoneEmail(String name, String phone, String email) {
        this.name = name;
        this.phone = phone;
        this.email = email;
    }
}

class NamePhone {
    String name;
    String phone;

    public NamePhone(String name, String phone) {
        this.name = name;
        this.phone = phone;
    }

    @Override
    public String toString() {
        return "{name: " + name + ", phone: " + phone + "}";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        NamePhone namePhone = (NamePhone) o;
        return Objects.equals(name, namePhone.name) &&
                Objects.equals(phone, namePhone.phone);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, phone);
    }
}
