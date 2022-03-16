package com.csis3175group6.bookapp.entities;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import java.sql.Timestamp;

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
    public String Isbn;
    @DatabaseField()
    public String Author;
    @DatabaseField()
    public String PublicationYear;
    @DatabaseField()
    public String Description;
    @DatabaseField(canBeNull = false)
    public int PageCount;
    @DatabaseField()
    public BookStatus Status;
    @DatabaseField()

    //Price is set when book is available for rent. Set to 0 if book is for borrowing.
    public double RentPrice;
    //Duration in days of rent time. Book become overdue when it is kept past this amount of day.
    @DatabaseField
    public int RentDuration;
    //Rented time is set when book status goes from ForRent to Rented. Used to calculate overdue time.
    @DatabaseField
    public Timestamp RentedTime;
    //The comment that owner can put in when creating a book renting post.
    @DatabaseField
    public String RentInformation;

    //Status of the book
    public enum BookStatus {
        Active, //Default when created. Available to read, lend, give away.
        ForRent,//Book become available to be rented or lent
        Giveaway,// Book become available to be given away
        Rented, //Book is being rented, owner can still change information. Holder can read.
        Overdue, //Book is overdue to be returned. Once book is past due date, status become Overdue. Once owner confirm that book is returned, overdue fee will be calculated based on RentDuration and RentPrice
        Inactive //Book no longer shown in owner book list. Still can be seen in read tracker.
    }

    public Book () {}
}
