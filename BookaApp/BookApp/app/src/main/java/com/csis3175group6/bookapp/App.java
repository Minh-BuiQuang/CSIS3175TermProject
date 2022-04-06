package com.csis3175group6.bookapp;

import android.app.Application;

import com.csis3175group6.bookapp.entities.User;

public class App extends Application {

    static App sInstance;

    @Override
    public void onCreate() {
        super.onCreate();
        sInstance = this;
    }

    public static App getInstance() {
        return App.sInstance;
    }

    public User User;
}
