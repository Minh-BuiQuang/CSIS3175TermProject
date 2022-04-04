package com.csis3175group6.bookapp.entities;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import java.sql.Timestamp;


@DatabaseTable
public class Message {
    public Long Id;
    public Long SenderId;
    public Long ReceiverId;
    public String Content;
    public Timestamp TimeStamp;
    public boolean FromSystem;
    public Message () {}
    public Message (Long id, Long senderId, Long receiverId, String content, Timestamp ts, boolean fromSystem) {
        Id = id;
        SenderId = senderId;
        ReceiverId = receiverId;
        Content = content;
        TimeStamp = ts;
        FromSystem = fromSystem;
    }
}
