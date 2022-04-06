package com.csis3175group6.bookapp.entities;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

public class User {
    public Long Id;
    public String Name;
    public String PinCode;
    public String Role;
    public String Address;
    public String ZipCode;
    public String Phone;
    public String Email;

    public static final String ROLE_USER = "user";
    public static final String ROLE_ADMIN = "admin";

    public User() {
    }

    public User(Long id, String name, String role, String pinCode, String address, String zipcode, String phone, String email) {
        Id = id;
        Name = name;
        Role = role;
        PinCode = pinCode;
        Address = address;
        ZipCode = zipcode;
        Phone = phone;
        Email = email;
    }
}
