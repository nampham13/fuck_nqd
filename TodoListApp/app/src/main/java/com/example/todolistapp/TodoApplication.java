package com.example.todolistapp;

import android.app.Application;

public class TodoApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        // Initialize the database
        TaskDatabase.getInstance(this);
    }
}