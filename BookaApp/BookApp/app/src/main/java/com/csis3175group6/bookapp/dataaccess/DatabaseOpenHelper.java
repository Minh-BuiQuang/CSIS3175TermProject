package com.csis3175group6.bookapp.dataaccess;
import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.csis3175group6.bookapp.R;
import com.csis3175group6.bookapp.entities.*;
import com.j256.ormlite.android.apptools.OrmLiteSqliteOpenHelper;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;
import java.sql.SQLException;
import java.util.List;

public class DatabaseOpenHelper extends SQLiteOpenHelper {

    final static String DATABASE_NAME = "BookManagement.db";
    final static int DATABASE_VERSION = 2;
    final static String TABLE1_Name = "User";
    final static String T1COL1 = "Id";
    final static String T1COL2 = "Name";
    final static String T1COL3 = "Pincode";
    //    final static String T1COL4 = "Role";
    final static String T1COL4 = "Address";
    final static String T1COL5 = "ZipCode";
    final static String T1COL6 = "Phone";
    final static String T1COL7 = "Email";

    final static String TABLE2_Name = "Book";
    final static String T2COL1 = "Id";
    final static String T2COL2 = "Title";
    final static String T2COL3 = "Owner";
    final static String T2COL4 = "Holder";
    final static String T2COL5 = "Isbn";
    final static String T2COL6 = "Author";
    final static String T2COL7 = "PublicationYear";
    final static String T2COL8 = "Description";
    final static String T2COL9 = "PageCount";
    final static String T2COL10 = "Status";

    final static String TABLE3_Name = "Message";
    final static String T3COL1 = "Id";
    final static String T3COL2 = "Sender";
    final static String T3COL3 = "Receiver";
    final static String T3COL4 = "Content";
    final static String T3COL5 = "TimeStamp";

    final static String TABLE4_Name = "ReadHistory";
    final static String T4COL1 = "Id";
    final static String T4COL2 = "Book";
    final static String T4COL3 = "Reader";
    final static String T4COL4 = "StartTime";
    final static String T4COL5 = "EndTime";
    final static String T4COL6 = "CurrentPage";

    public DatabaseOpenHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        SQLiteDatabase db = this.getWritableDatabase();
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String sQuery = "CREATE TABLE " + TABLE1_Name + "(" + T1COL1 + " INTEGER PRIMARY KEY," +
                T1COL2 + " Text," + T1COL3 + " TEXT," + T1COL4 + " Text," + T1COL5 +
                " Text," + T1COL6 + " Text," + T1COL7 + " Text)" ;
        db.execSQL(sQuery);

        String bQuery = "CREATE TABLE " + TABLE2_Name + "(" + T2COL1 + " INTEGER PRIMARY KEY," +
                T2COL2 + " Text," + T2COL3 + " TEXT," + T2COL4 + " Text," + T2COL5 +
                " Text," + T2COL6 + " Text," + T2COL7 + " Text," + T2COL8 +
                " Text," + T2COL9 + " Text," + T2COL10 + " Text)";
        db.execSQL(bQuery);

        String mQuery = "CREATE TABLE " + TABLE3_Name + "(" + T3COL1 + " INTEGER PRIMARY KEY," +
                T3COL2 + " Text," + T3COL3 + " Text," + T3COL4 + " Text," + T3COL5 +
                " Text)";
        db.execSQL(mQuery);

        String hQuery = "CREATE TABLE " + TABLE4_Name + "(" + T4COL1 + " INTEGER PRIMARY KEY," +
                T4COL2 + " Text," + T4COL3 + " Text," + T4COL4 + " Text," + T4COL5 + " Text," + T4COL6 +
                " Text)";
        db.execSQL(hQuery);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE1_Name);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE2_Name);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE3_Name);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE4_Name);
        onCreate(db);
    }

    public boolean addUserRecord(String n,String pin,String address, String zipcode, String phone, String email){
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        ContentValues value = new ContentValues();
        value.put(T1COL2,n);
        value.put(T1COL3,pin);
        value.put(T1COL4,address);
        value.put(T1COL5,zipcode);
        value.put(T1COL6,phone);
        value.put(T1COL7,email);

        long r = sqLiteDatabase.insert(TABLE1_Name,null,value);
        if(r>0)
            return true;
        else
            return false;
    }

    public boolean addBookRecord(String title, String isbn, String author, String publishYear, String description, String pageCount){
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        ContentValues value = new ContentValues();
        value.put(T2COL2,title);
        value.put(T2COL5,isbn);
        value.put(T2COL6,author);
        value.put(T2COL7,publishYear);
        value.put(T2COL8,description);
        value.put(T2COL9,pageCount);

        long r = sqLiteDatabase.insert(TABLE2_Name,null,value);
        if(r>0)
            return true;
        else
            return false;
    }
//    final static String DATABASE_NAME = "BookManagement.db";
//    final static int VERSION = 1;
//
//    public DatabaseOpenHelper(Context context) {
//        super(context, DATABASE_NAME, null, VERSION, R.raw.ormlite_config2);
//    }
//
//    @Override
//    public void onCreate(SQLiteDatabase database, ConnectionSource connectionSource) {
//        try {
//            TableUtils.createTable(connectionSource, User.class);
//            TableUtils.createTable(connectionSource, Book.class);
//            TableUtils.createTable(connectionSource, ReadHistory.class);
//            TableUtils.createTable(connectionSource, Message.class);
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
//    }
//
//    @Override
//    public void onUpgrade(SQLiteDatabase database, ConnectionSource connectionSource, int oldVersion, int newVersion) {
//        try {
//            TableUtils.dropTable(connectionSource, Message.class, true);
//            TableUtils.dropTable(connectionSource, ReadHistory.class, true);
//            TableUtils.dropTable(connectionSource, Book.class, true);
//            TableUtils.dropTable(connectionSource, User.class, true);
//            onCreate(database, connectionSource);
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
//    }
//
//    public boolean insertBook(Book book){
//        try {
//            Dao<Book, Long> bookDao = getDao(Book.class);
//            int affectedRows = bookDao.create(book);
//            return affectedRows > 0;
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
//        return false;
//    }
//
//    public boolean updateBook(Book book) {
//
//        try {
//            Dao<Book, Long> bookDao = getDao(Book.class);
//            int affectedRows = bookDao.update(book);
//            return affectedRows > 0;
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
//        return false;
//    }
//
//    public List<Book> getBooksByOwnerId(Long ownerId){
//        try {
//            Dao<Book, Long> bookDao = getDao(Book.class);
//            List<Book> books = bookDao.queryBuilder().where().eq("ownerId", ownerId).query();
//            return books;
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
//        return null;
//    }
//
//    public List<Book> getBooksByHolderId(Long holderId){
//        try {
//            Dao<Book, Long> bookDao = getDao(Book.class);
//            List<Book> books = bookDao.queryBuilder().where().eq("holderId", holderId).query();
//            return books;
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
//        return null;
//    }
//
//    public List<User> getUsers() {
//        try {
//            Dao<User, Long> userDao = getDao(User.class);
//            return userDao.queryForAll();
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
//        return null;
//    }
//
//    public User getUserByCredential(String name, String pinCode) {
//        try {
//            Dao<User, Long> userDao = getDao(User.class);
//            User user = userDao.queryBuilder().where().eq("Name", name).and().eq("pinCode", pinCode).queryForFirst();
//            return user;
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
//        return null;
//    }
//
//    public boolean updateUser(User user){
//        try {
//            Dao<User, Long> userDao = getDao(User.class);
//            int affectedRows = userDao.update(user);
//            return affectedRows > 0;
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
//        return false;
//    }
}
