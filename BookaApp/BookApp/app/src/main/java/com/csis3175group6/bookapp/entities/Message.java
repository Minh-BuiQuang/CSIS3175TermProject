package com.csis3175group6.bookapp.entities;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import java.sql.Timestamp;


@DatabaseTable
public class Message {
    @DatabaseField(generatedId = true)
    public Long Id;
    @DatabaseField(canBeNull = false, foreign = true)
    public User Sender;
    @DatabaseField(canBeNull = false, foreign = true)
    public User Receiver;
    @DatabaseField(canBeNull = false)
    public String Content;
    @DatabaseField(canBeNull = false)
    public Timestamp TimeStamp;
    public Message () {}
}
