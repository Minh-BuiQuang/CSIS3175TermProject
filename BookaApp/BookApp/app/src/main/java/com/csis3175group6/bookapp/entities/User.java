package com.csis3175group6.bookapp.entities;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable
public class User {
    @DatabaseField(generatedId = true)
    public Long Id;
    @DatabaseField(canBeNull = false, unique = true)
    public String Name;
    @DatabaseField(canBeNull = false)
    public String PinCode;
    @DatabaseField(canBeNull = false)
    public UserRole Role;
    @DatabaseField(canBeNull = false)
    public String Address;
    @DatabaseField(canBeNull = false)
    public String ZipCode;
    @DatabaseField
    public String Phone;
    @DatabaseField
    public String Email;

    public enum UserRole {
        User,
        Admin
    }

    public User(){}

    public User (String name, UserRole role, String pinCode, String address, String zipCode, String phone, String email) {
        Name = name;
        Role = role;
        PinCode = pinCode;
        Address = address;
        ZipCode = zipCode;
        Phone = phone;
        Email = email;
    }
}
