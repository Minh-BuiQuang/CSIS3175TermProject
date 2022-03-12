package com.csis3175group6.bookapp.entities;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import java.sql.Timestamp;

@DatabaseTable
public class ReadHistory {
    @DatabaseField(generatedId = true)
    public Long Id;
    @DatabaseField(canBeNull = false, foreign = true)
    public Book Book;
    @DatabaseField(canBeNull = false,foreign = true)
    public User Reader;
    @DatabaseField(canBeNull = false)
    public Timestamp StartTime;
    @DatabaseField
    public Timestamp EndTime;
    @DatabaseField(defaultValue = "0")
    public int CurrentPage;

    public ReadHistory(){};
}
