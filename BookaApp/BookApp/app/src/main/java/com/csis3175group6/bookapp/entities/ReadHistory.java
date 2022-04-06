package com.csis3175group6.bookapp.entities;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import java.sql.Timestamp;

@DatabaseTable
public class ReadHistory {
    public Long Id;
    public Book Book;
    public User Reader;
    public Timestamp StartTime;
    public Timestamp EndTime;
    public int CurrentPage;

    public ReadHistory() {
    }

}
