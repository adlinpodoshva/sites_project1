package com.example.misha.data;

import android.database.sqlite.SQLiteDatabase;

public class User {
    private final static String KEY_ID = "id";
    private final static String TABLE_NAME = "User";
    private final static String KEY_EMAIL = "email";
    private final static String KEY_PASSWORD = "password";
    private final static String KEY_FULLNAME = "fullName";

    private String email;
    private String password;
    private String fullName;

    public User(String email, String password, String fullName) {
        this.email = email;
        this.password = password;
        this.fullName = fullName;
    }

    public static void createTable(SQLiteDatabase db) {
        String query = "CREATE TABLE " + TABLE_NAME + "("
                + KEY_ID + " INTEGER PRIMARY KEY," + KEY_EMAIL + " TEXT,"
                + KEY_PASSWORD + " TEXT," + KEY_FULLNAME + " TEXT" + ")";
        db.execSQL(query);
    }

    public static String getTableName() {
        return TABLE_NAME;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }
}
