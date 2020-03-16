package com.misha.sitesproject.data;

// empty constructor for firebase
public class User {
//    private final static String KEY_ID = "id";
//    private final static String TABLE_NAME = "User";
//    private final static String KEY_EMAIL = "email";
//    private final static String KEY_PASSWORD = "password";
//    private final static String KEY_FULLNAME = "fullName";

    private String email;
    private String fullName;
    private String phone;

    public User() {

    }

    public User(String email, String fullName, String phone) {
        this.email = email;
        this.fullName = fullName;
        this.phone = phone;
    }

//    public static void createTable(SQLiteDatabase db) {
//        String query = "CREATE TABLE " + TABLE_NAME + "("
//                + KEY_ID + " INTEGER PRIMARY KEY," + KEY_EMAIL + " TEXT,"
//                + KEY_PASSWORD + " TEXT," + KEY_FULLNAME + " TEXT" + ")";
//        db.execSQL(query);
//    }
//
//    public static String getTableName() {
//        return TABLE_NAME;
//    }

    public String getEmail() {
        return email;
    }

    public String getFullName() {
        return fullName;
    }

    public String getPhone() {
        return phone;
    }
}
