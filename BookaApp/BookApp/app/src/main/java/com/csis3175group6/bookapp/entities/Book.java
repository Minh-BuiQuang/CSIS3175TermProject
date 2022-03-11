package com.csis3175group6.bookapp.entities;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable()
public class Book{
    @DatabaseField(generatedId = true)
    public Long Id;
    @DatabaseField(canBeNull = false)
    public String Title;
    @DatabaseField(canBeNull = false, foreign = true)
    public User Owner;
    @DatabaseField(canBeNull = false, foreign = true)
    public User Holder;
    @DatabaseField()
    public Long Isbn;
    @DatabaseField()
    public String Author;
    @DatabaseField()
    public int PublicationYear;
    @DatabaseField()
    public BookStatus Status;
    @DatabaseField()
    public double RentPrice;
    @DatabaseField()
    public String Description;
    @DatabaseField()
    public int PageCount;

    //Status of the book
    public enum BookStatus {
        Active, //Default when created. Available to read, lend, give away.
        ForRent,//Book become available to be rented or lended
        Giveaway,// Book become available to be given away
        Rented, //Book is being rented, owner can still change information. Holder can read.
        Inactive //Book no longer shown in owner book list. Still can be seen in read tracker.
    }

    public Book () {}
}
