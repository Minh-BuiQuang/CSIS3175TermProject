package com.csis3175group6.bookapp.entities;

import java.sql.Timestamp;

public class Request {
    public Long Id;
    public Long RequesterId;
    public Long BookId;
    public Timestamp RequestTimeStamp;
    public Boolean HasCompleted;

    public Request() {}

    public Request (Long id, Long requesterId, Long bookId, Timestamp requestTimeStamp, Boolean hasCompleted) {
        Id = id;
        RequesterId = requesterId;
        BookId = bookId;
        RequestTimeStamp = requestTimeStamp;
        HasCompleted = hasCompleted;
    }
}



