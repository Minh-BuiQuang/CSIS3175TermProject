package com.csis3175group6.bookapp.entities;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import java.sql.Timestamp;

public class Book{
    public Long Id;
    public String Title;
    public Long OwnerId;
    public Long HolderId;
    public String Isbn;
    public String Author;
    public String PublicationYear;
    public String Description;
    public int PageCount;
    public String Status;

    //Price is set when book is available for rent. Set to 0 if book is for borrowing.
    public double RentPrice;
    //Duration in days of rent time. Book become overdue when it is kept past this amount of day.
    public int RentDuration;
    //Rented time is set when book status goes from ForRent to Rented. Used to calculate overdue time.
    public Timestamp RentedTime;
    //The comment that owner can put in when creating a book renting post.
    public String RentInformation;

    public static final String STATUS_ACTIVE = "Active"; //Default when created. Available to read, lend, give away.
    public static final String STATUS_FOR_RENT = "ForRent";//Book become available to be rented or lent
    public static final String STATUS_GIVEAWAY = "Giveaway";// Book become available to be given away
    public static final String STATUS_RENTED = "Rented"; //Book is being rented, owner can still change information. Holder can read.
    public static final String STATUS_OVERDUE = "Overdue"; //Book is overdue to be returned. Once book is past due date, status become Overdue. Once owner confirm that book is returned, overdue fee will be calculated based on RentDuration and RentPrice
    public static final String STATUS_INACTIVE = "Inactive"; //Book no longer shown in owner book list. Still can be seen in read tracker.

    public Book(){}

    public Book (Long id, String title, Long ownerId, Long holderId, String isbn, String author, String publicationYear, String description, int pageCount, String status) {

        Id = id;
        Title = title;
        OwnerId = ownerId;
        HolderId = holderId;
        Isbn = isbn;
        Author = author;
        PublicationYear = publicationYear;
        Description = description;
        PageCount = pageCount;
        Status = status;
    }
}
