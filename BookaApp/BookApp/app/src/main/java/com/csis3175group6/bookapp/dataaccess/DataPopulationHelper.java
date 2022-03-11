package com.csis3175group6.bookapp.dataaccess;

import android.content.Context;

import com.csis3175group6.bookapp.entities.User;
import com.j256.ormlite.dao.Dao;

import java.sql.SQLException;

public class DataPopulationHelper {

    public static void populateUser(Context context) {
        try {
            DatabaseOpenHelper db = new DatabaseOpenHelper(context);
            Dao<User, Long> UserDao = db.getDao(User.class);
            UserDao.create(new User("Admin", User.UserRole.Admin, "1111", "02 Crest Line Point","a3gr5d","7784561235","ccamelli0@wufoo.com"));
            UserDao.create(new User("Bruce", User.UserRole.User, "1234","36851 Sunbrook Center", "a5dy1f", "778465151", "cdunhill1@blinklist.com"));
            UserDao.create(new User("Edward", User.UserRole.User, "1234","425 Orin Circle", "r4xe4d", "6041114567", "lshoemark2@furl.net"));
            UserDao.create(new User("Barbara", User.UserRole.User, "1234","5 Havey Road", "e5ga6r", "6045451133", "ldebischop3@xinhuanet.com"));
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
