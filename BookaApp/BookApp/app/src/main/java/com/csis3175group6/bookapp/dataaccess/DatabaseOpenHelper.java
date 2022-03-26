package com.csis3175group6.bookapp.dataaccess;
import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
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
    final static int DATABASE_VERSION = 7;
    final static String TABLE_USER = "User";
    final static String USER_ID = "UserId";
    final static String USER_NAME = "Name";
    final static String USER_PINCODE = "Pincode";
    final static String USER_ROLE = "Role";
    final static String USER_ADDRESS = "Address";
    final static String USER_ZIPCODE = "ZipCode";
    final static String USER_PHONE = "Phone";
    final static String USER_EMAIL = "Email";

    final static String TABLE_BOOK = "Book";
    final static String BOOK_ID = "BookId";
    final static String BOOK_TITLE = "Title";
    final static String BOOK_OWNER_ID = "OwnerId";
    final static String BOOK_HOLDER_ID = "HolderId";
    final static String BOOK_ISBN = "Isbn";
    final static String BOOK_AUTHOR = "Author";
    final static String BOOK_PUBLISH_YEAR = "PublicationYear";
    final static String BOOK_DESCRIPTION = "Description";
    final static String BOOK_PAGE_COUNT = "PageCount";
    final static String BOOK_STATUS = "Status";
    final static String BOOK_RENT_PRICE = "RentPrice";
    final static String BOOK_RENT_DURATION = "RentDuration";
    final static String BOOK_RENTED_TIME = "RentedTime";
    final static String BOOK_RENT_INFO = "RentInformation";

    final static String TABLE_MESSAGE = "Message";
    final static String MESSAGE_ID = "MessageId";
    final static String MESSAGE_SENDER_ID = "SenderId";
    final static String MESSAGE_RECEIVER_ID = "ReceiverId";
    final static String MESSAGE_CONTENT = "Content";
    final static String MESSAGE_TIMESTAMP = "MessageTimeStamp";

    final static String TABLE_HISTORY = "ReadHistory";
    final static String HISTORY_ID = "HistoryId";
    final static String HISTORY_BOOK_ID = "BookId";
    final static String HISTORY_READER_ID = "ReaderId";
    final static String HISTORY_START = "StartTime";
    final static String HISTORY_END = "EndTime";
    final static String HISTORY_CURRENT_PAGE = "CurrentPage";

    final static String TABLE_REQUEST = "Request";
    final static String REQUEST_ID = "RequestId";
    final static String REQUESTER_ID = "RequesterId";
    final static String REQUEST_BOOK_ID = "BookId";
    final static String REQUEST_TIMESTAMP = "RequestTimestamp";
    final static String HAS_COMPLETED = "HasCompleted";


    public DatabaseOpenHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        SQLiteDatabase db = this.getWritableDatabase();
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String sQuery = "CREATE TABLE " + TABLE_USER + "(" + USER_ID + " INTEGER PRIMARY KEY," +
                USER_NAME + " TEXT," + USER_ROLE + " TEXT," + USER_PINCODE + " TEXT," + USER_ADDRESS + " TEXT," + USER_ZIPCODE +
                " TEXT," + USER_PHONE + " TEXT," + USER_EMAIL + " TEXT)" ;
        db.execSQL(sQuery);

        String bQuery = "CREATE TABLE " + TABLE_BOOK + "(" + BOOK_ID + " INTEGER PRIMARY KEY," +
                BOOK_TITLE + " TEXT," + BOOK_OWNER_ID + " INTEGER," + BOOK_HOLDER_ID + " INTEGER," + BOOK_ISBN +
                " TEXT," + BOOK_AUTHOR + " TEXT," + BOOK_PUBLISH_YEAR + " TEXT," + BOOK_DESCRIPTION +
                " TEXT," + BOOK_PAGE_COUNT + " INTEGER," + BOOK_STATUS + " INTEGER," + BOOK_RENT_PRICE +
                " NUMBER," + BOOK_RENT_DURATION + " INTEGER," + BOOK_RENTED_TIME + " TEXT," + BOOK_RENT_INFO + " TEXT,"
                + "FOREIGN KEY(" + BOOK_OWNER_ID +")" + " REFERENCES " + TABLE_USER + "(" + USER_ID + ")," +
                "FOREIGN KEY(" + BOOK_HOLDER_ID +")" + " REFERENCES " + TABLE_USER + "(" + USER_ID + ")" + ")" ;
        db.execSQL(bQuery);

        String mQuery = "CREATE TABLE " + TABLE_MESSAGE + "(" + MESSAGE_ID + " INTEGER PRIMARY KEY," +
                MESSAGE_SENDER_ID + " INTEGER," + MESSAGE_RECEIVER_ID + " INTEGER," + MESSAGE_CONTENT + " TEXT," + MESSAGE_TIMESTAMP +
                " TEXT," + "FOREIGN KEY(" + MESSAGE_SENDER_ID +")" + " REFERENCES " + TABLE_USER + "(" + USER_ID + ")," +
                "FOREIGN KEY(" + MESSAGE_RECEIVER_ID +")" + " REFERENCES " + TABLE_USER + "(" + USER_ID + ")" + ")";
        db.execSQL(mQuery);

        String hQuery = "CREATE TABLE " + TABLE_HISTORY + "(" + HISTORY_ID + " INTEGER PRIMARY KEY," +
                HISTORY_BOOK_ID + " INTEGER," + HISTORY_READER_ID + " INTEGER," + HISTORY_START + " TEXT," + HISTORY_END + " TEXT," + HISTORY_CURRENT_PAGE +
                " INTEGER," + "FOREIGN KEY(" + HISTORY_BOOK_ID +")" + " REFERENCES " + TABLE_BOOK + "(" + BOOK_ID + ")," +
                "FOREIGN KEY(" + HISTORY_READER_ID +")" + " REFERENCES " + TABLE_USER + "(" + USER_ID + ")" + ")";
        db.execSQL(hQuery);

        String rQuery = "CREATE TABLE " + TABLE_REQUEST + "(" + REQUEST_ID + " INTEGER PRIMARY KEY," + REQUEST_BOOK_ID + " INTEGER," + REQUESTER_ID +
                " INTEGER REFERENCES " + TABLE_USER + "(" + USER_ID + ")," + REQUEST_TIMESTAMP + " TEXT," + HAS_COMPLETED + " BOOLEAN," + "FOREIGN KEY(" + REQUEST_BOOK_ID + ")" + " REFERENCES " +
                TABLE_BOOK + "(" + BOOK_ID + ")" + ")";
        db.execSQL(rQuery);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_USER);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_BOOK);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_MESSAGE);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_HISTORY);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_REQUEST);
        onCreate(db);
    }

    @Override
    public void onConfigure(SQLiteDatabase db) {
        super.onConfigure(db);
        db.setForeignKeyConstraintsEnabled(true);
    }

    public boolean addUserRecord(User user){
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        ContentValues value = new ContentValues();
        value.put(USER_NAME,user.Name);
        value.put(USER_PINCODE,user.PinCode);
        value.put(USER_ROLE, user.ROLE_USER);
        value.put(USER_ADDRESS,user.Address);
        value.put(USER_ZIPCODE,user.ZipCode);
        value.put(USER_PHONE,user.Phone);
        value.put(USER_EMAIL,user.Email);

        long r = sqLiteDatabase.insert(TABLE_USER,null,value);
        return  r>0;
    }
    //OWNER AND HOLDER == USER_ID, iNsert update, delete tra ve boolean
    public boolean addBookRecord(String title, String isbn, String author, String publishYear, String description, String pageCount){
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        ContentValues value = new ContentValues();
        value.put(BOOK_TITLE,title);
        value.put(BOOK_ISBN,isbn);
        value.put(BOOK_AUTHOR,author);
        value.put(BOOK_PUBLISH_YEAR,publishYear);
        value.put(BOOK_DESCRIPTION,description);
        value.put(BOOK_PAGE_COUNT,pageCount);

        long r = sqLiteDatabase.insert(TABLE_BOOK,null,value);
        if(r>0)
            return true;
        else
            return false;
    }

    //tra ve object
    public Cursor viewUserRecord(){
        SQLiteDatabase sqLiteDatabase = this.getReadableDatabase();
        String query = "SELECT * FROM " + TABLE_BOOK;
        Cursor c =sqLiteDatabase.rawQuery(query,null);
        return c;
    }

    public boolean updateUserRec(String name, String pass, String address, String zipcode, String phone, String email){
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(USER_NAME,name);
        values.put(USER_PINCODE,pass);
        values.put(USER_ADDRESS,address);
        values.put(USER_ZIPCODE,zipcode);
        values.put(USER_PHONE,phone);
        values.put(USER_EMAIL,email);

        Cursor cursor = sqLiteDatabase.rawQuery("Select * from " + TABLE_USER + " where " + USER_NAME + " = ?", new String[]{name});
        if (cursor.getCount() > 0) {
            long result = sqLiteDatabase.update(TABLE_USER, values, "name=?", new String[]{name});
            if (result == -1) {
                return false;
            } else {
                return true;
            }
        } else {
            return false;
        }
    }
    //check if user exist or not
    public User getUser(String name, String pincode) {
        // array of columns to fetch user data
        SQLiteDatabase db = this.getReadableDatabase();
        // selection arguments
        String[] selectionArgs = {name,pincode};
        // query user table with conditions
        Cursor cursor = db.rawQuery("select * from " + TABLE_USER + " where name = ? AND pincode = ?", selectionArgs);
        User user = null;
        if(cursor.getCount() > 0)
        {
            cursor.moveToNext();
            user = ToUser(cursor);
        }
        cursor.close();
        db.close();
        return user;
    }

    @SuppressLint("Range")
    private User ToUser(Cursor c) {
        User user = new User();
        user.Id = c.getLong(c.getColumnIndex(USER_ID));
        user.Name = c.getString(c.getColumnIndex(USER_NAME));
        user.Role = c.getString(c.getColumnIndex(USER_ROLE));
        user.PinCode = c.getString(c.getColumnIndex(USER_PINCODE));
        user.Address = c.getString(c.getColumnIndex(USER_ADDRESS));
        user.ZipCode = c.getString(c.getColumnIndex(USER_ZIPCODE));
        user.Phone = c.getString(c.getColumnIndex(USER_PHONE));
        user.Email = c.getString(c.getColumnIndex(USER_EMAIL));
        return user;
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
