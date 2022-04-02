package com.csis3175group6.bookapp.dataaccess;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import com.csis3175group6.bookapp.entities.*;

import java.sql.Time;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Arrays;

public class DatabaseOpenHelper extends SQLiteOpenHelper {

    final static String DATABASE_NAME = "BookManagement.db";
    final static int DATABASE_VERSION = 9;
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
    final static String MESSAGE_FROM_SYSTEM = "FromSystem";
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
                " TEXT," + USER_PHONE + " TEXT," + USER_EMAIL + " TEXT)";
        db.execSQL(sQuery);

        String bQuery = "CREATE TABLE " + TABLE_BOOK + "(" + BOOK_ID + " INTEGER PRIMARY KEY," +
                BOOK_TITLE + " TEXT," + BOOK_OWNER_ID + " INTEGER," + BOOK_HOLDER_ID + " INTEGER," + BOOK_ISBN +
                " TEXT," + BOOK_AUTHOR + " TEXT," + BOOK_PUBLISH_YEAR + " TEXT," + BOOK_DESCRIPTION +
                " TEXT," + BOOK_PAGE_COUNT + " INTEGER," + BOOK_STATUS + " INTEGER," + BOOK_RENT_PRICE +
                " NUMBER," + BOOK_RENT_DURATION + " INTEGER," + BOOK_RENTED_TIME + " TIMESTAMP," + BOOK_RENT_INFO + " TEXT,"
                + "FOREIGN KEY(" + BOOK_OWNER_ID + ")" + " REFERENCES " + TABLE_USER + "(" + USER_ID + ")," +
                "FOREIGN KEY(" + BOOK_HOLDER_ID + ")" + " REFERENCES " + TABLE_USER + "(" + USER_ID + ")" + ")";
        db.execSQL(bQuery);

        String mQuery = "CREATE TABLE " + TABLE_MESSAGE + "(" + MESSAGE_ID + " INTEGER PRIMARY KEY," +
                MESSAGE_SENDER_ID + " INTEGER," + MESSAGE_RECEIVER_ID + " INTEGER," + MESSAGE_CONTENT + " TEXT," + MESSAGE_TIMESTAMP +
                " TIMESTAMP," + MESSAGE_FROM_SYSTEM + " BOOLEAN, " + "FOREIGN KEY(" + MESSAGE_SENDER_ID + ")" + " REFERENCES " + TABLE_USER + "(" + USER_ID + ")," +
                "FOREIGN KEY(" + MESSAGE_RECEIVER_ID + ")" + " REFERENCES " + TABLE_USER + "(" + USER_ID + ")" + ")";
        db.execSQL(mQuery);

        String hQuery = "CREATE TABLE " + TABLE_HISTORY + "(" + HISTORY_ID + " INTEGER PRIMARY KEY," +
                HISTORY_BOOK_ID + " INTEGER," + HISTORY_READER_ID + " INTEGER," + HISTORY_START + " TEXT," + HISTORY_END + " TEXT," + HISTORY_CURRENT_PAGE +
                " INTEGER," + "FOREIGN KEY(" + HISTORY_BOOK_ID + ")" + " REFERENCES " + TABLE_BOOK + "(" + BOOK_ID + ")," +
                "FOREIGN KEY(" + HISTORY_READER_ID + ")" + " REFERENCES " + TABLE_USER + "(" + USER_ID + ")" + ")";
        db.execSQL(hQuery);

        String rQuery = "CREATE TABLE " + TABLE_REQUEST + "(" + REQUEST_ID + " INTEGER PRIMARY KEY," + REQUEST_BOOK_ID + " INTEGER," + REQUESTER_ID +
                " INTEGER REFERENCES " + TABLE_USER + "(" + USER_ID + ")," + REQUEST_TIMESTAMP + " TIMESTAMP," + HAS_COMPLETED + " BOOLEAN," + "FOREIGN KEY(" + REQUEST_BOOK_ID + ")" + " REFERENCES " +
                TABLE_BOOK + "(" + BOOK_ID + ")" + ")";
        db.execSQL(rQuery);

        PopulateData(db);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_MESSAGE);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_HISTORY);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_REQUEST);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_BOOK);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_USER);
        onCreate(db);
    }

    @Override
    public void onConfigure(SQLiteDatabase db) {
        super.onConfigure(db);
        db.setForeignKeyConstraintsEnabled(true);
    }

    public boolean addUserRecord(User user) {
        return addUserRecord(user, null);
    }
    public boolean addUserRecord(User user, SQLiteDatabase sqLiteDatabase) {
        if(sqLiteDatabase == null) sqLiteDatabase = this.getWritableDatabase();
        ContentValues value = new ContentValues();
        value.put(USER_NAME, user.Name);
        value.put(USER_PINCODE, user.PinCode);
        value.put(USER_ROLE, user.Role);
        value.put(USER_ADDRESS, user.Address);
        value.put(USER_ZIPCODE, user.ZipCode);
        value.put(USER_PHONE, user.Phone);
        value.put(USER_EMAIL, user.Email);

        long r = sqLiteDatabase.insert(TABLE_USER, null, value);
        return r > 0;
    }
    public boolean addBookRecord(Book book) {
        return addBookRecord(book, null);
    }
    //OWNER AND HOLDER == USER_ID, insert update, delete return boolean
    public boolean addBookRecord(Book book, SQLiteDatabase sqLiteDatabase) {
        if(sqLiteDatabase == null) sqLiteDatabase = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(BOOK_TITLE, book.Title);
        values.put(BOOK_OWNER_ID, book.OwnerId);
        values.put(BOOK_HOLDER_ID, book.HolderId);
        values.put(BOOK_ISBN, book.Isbn);
        values.put(BOOK_AUTHOR, book.Author);
        values.put(BOOK_PUBLISH_YEAR, book.PublicationYear);
        values.put(BOOK_DESCRIPTION, book.Description);
        values.put(BOOK_PAGE_COUNT, book.PageCount);
        values.put(BOOK_STATUS, book.Status);
        values.put(BOOK_RENT_DURATION, book.RentDuration);
        values.put(BOOK_RENT_PRICE, book.RentPrice);
        values.put(BOOK_RENTED_TIME, book.RentedTime.getTime());

        long r = sqLiteDatabase.insert(TABLE_BOOK, null, values);
        return r > 0;
    }

    public boolean addShareRecord(Request request, SQLiteDatabase sqLiteDatabase) {
        if(sqLiteDatabase == null) sqLiteDatabase = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(REQUESTER_ID, request.RequesterId);
        values.put(REQUEST_BOOK_ID, request.BookId);
        values.put(REQUEST_TIMESTAMP, request.RequestTimeStamp.getTime());
        values.put(HAS_COMPLETED, request.HasCompleted);

        long r = sqLiteDatabase.insert(TABLE_REQUEST, null, values);
        return r > 0;
    }

    //tra ve object
    public Cursor viewUserRecord() {
        SQLiteDatabase sqLiteDatabase = this.getReadableDatabase();
        String query = "SELECT * FROM " + TABLE_BOOK;
        Cursor c = sqLiteDatabase.rawQuery(query, null);
        return c;
    }

    public boolean updateUserRec(User user) {
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(USER_ID, user.Id);
        values.put(USER_NAME, user.Name);
        values.put(USER_PINCODE, user.PinCode);
        values.put(USER_ROLE, user.Role);
        values.put(USER_ADDRESS, user.Address);
        values.put(USER_ZIPCODE, user.ZipCode);
        values.put(USER_PHONE, user.Phone);
        values.put(USER_EMAIL, user.Email);
        long result = sqLiteDatabase.update(TABLE_USER, values, USER_ID + "=?", new String[]{user.Id.toString()});
        sqLiteDatabase.close();
        return result > 0;
    }

    //check if user exist or not
    public User getUser(String name, String pincode) {
        // array of columns to fetch user data
        SQLiteDatabase db = this.getReadableDatabase();
        // selection arguments
        String[] selectionArgs = {name, pincode};
        // query user table with conditions
        Cursor cursor = db.rawQuery("select * from " + TABLE_USER + " where " + USER_NAME + " = ? AND " + USER_PINCODE + " = ?", selectionArgs);
        User user = null;
        if (cursor.getCount() > 0) {
            cursor.moveToNext();
            user = ToUser(cursor);
        }
        cursor.close();
        db.close();
        return user;
    }

    public User getUser(Long id) {
        // array of columns to fetch user data
        SQLiteDatabase db = this.getReadableDatabase();
        // selection arguments
        String[] selectionArgs = {id.toString()};
        // query user table with conditions
        Cursor cursor = db.rawQuery("select * from " + TABLE_USER + " where " + USER_ID + " =?", selectionArgs);
        User user = null;
        if (cursor.getCount() > 0) {
            cursor.moveToNext();
            user = ToUser(cursor);
        }
        cursor.close();
        db.close();
        return user;
    }

    public ArrayList<User> getUsers() {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("select * from " + TABLE_USER, null);
        User[] users = new User[cursor.getCount()];
        int index = 0;
        while(cursor.moveToNext())
        {
            users[index++] = ToUser(cursor);
        }
        cursor.close();
        db.close();
        return new ArrayList<User>(Arrays.asList(users));
    }

    public ArrayList<Book> getBooks() {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("select * from " + TABLE_BOOK, null);
        Book[] books = new Book[cursor.getCount()];
        int index = 0;
        while(cursor.moveToNext())
        {
            books[index++] = ToBook(cursor);
        }
        cursor.close();
        db.close();
        return new ArrayList<Book>(Arrays.asList(books));
    }

    public ArrayList<Book> getBooksByOwnerId(Long id){
        SQLiteDatabase db = this.getReadableDatabase();
        String[] selectionArgs = {id.toString()};
        //Cursor c = db.rawQuery("select * from " + TABLE_BOOK + " inner join " + TABLE_USER + " on Book.OwnerId =?", selectionArgs);
        Cursor c = db.rawQuery("Select * from Book where Book.OwnerId =?",selectionArgs);
        Book[] books = new Book[c.getCount()];
        int index = 0;
        while(c.moveToNext())
        {
            books[index++] = ToBook(c);
        }
        c.close();
        db.close();
        return new ArrayList<Book>(Arrays.asList(books));
    }

    public Request getRequest(Long id) {
        SQLiteDatabase db = this.getReadableDatabase();
        String[] selectionArgs = {id.toString()};
        Cursor cursor = db.rawQuery("select * from " + TABLE_REQUEST + " where " + REQUEST_ID + " =?", selectionArgs);
        Request request = null;
        if (cursor.getCount() > 0) {
            cursor.moveToNext();
            request = ToRequest(cursor);
        }
        cursor.close();
        db.close();
        return request;
    }

    public ArrayList<Request> getRequests() {
       SQLiteDatabase db = this.getReadableDatabase();
       Cursor cursor = db.rawQuery("select * from " + TABLE_REQUEST, null);
       Request[] requests = new Request[cursor.getCount()];
       int index = 0;
       while(cursor.moveToNext()) {
           requests[index++] = ToRequest(cursor);
       }
       cursor.close();
       db.close();
       return new ArrayList<Request>(Arrays.asList(requests));
    }

    public Cursor getBookById(Long id) {
    SQLiteDatabase db = this.getWritableDatabase();
        String[] selectionArgs = {id.toString()};
    String query = "Select * from Book where Book.OwnerId =?";
    Cursor cursor = db.rawQuery(query,selectionArgs);

    return cursor;
    }

    public boolean AddMessage(Message message) {
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(MESSAGE_ID, message.Id);
        values.put(MESSAGE_CONTENT, message.Content);
        values.put(MESSAGE_SENDER_ID, message.SenderId);
        values.put(MESSAGE_RECEIVER_ID, message.ReceiverId);
        values.put(MESSAGE_TIMESTAMP, message.TimeStamp.getTime());
        values.put(MESSAGE_FROM_SYSTEM, message.FromSystem);
        long result = sqLiteDatabase.insert(TABLE_MESSAGE,null, values);
        sqLiteDatabase.close();
        return result > 0;

    }

    public ArrayList<Message> GetMessage(Long senderId, Long receiverId) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("select * from " + TABLE_MESSAGE +
                " where " + MESSAGE_SENDER_ID + "=? and " + MESSAGE_RECEIVER_ID + "=?",
                new String[]{senderId.toString(),receiverId.toString()});
        ArrayList<Message> messages = new ArrayList<>();
        while(cursor.moveToNext()) messages.add(ToMessage(cursor));
        cursor.close();
        db.close();
        return messages;
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

    @SuppressLint("Range")
    private Book ToBook(Cursor c) {
        Book book = new Book();
        book.Id = c.getLong(c.getColumnIndex(BOOK_ID));
        book.OwnerId = c.getLong(c.getColumnIndex(BOOK_OWNER_ID));
        book.HolderId = c.getLong(c.getColumnIndex(BOOK_HOLDER_ID));
        book.Title = c.getString(c.getColumnIndex(BOOK_TITLE));
        book.Isbn = c.getString(c.getColumnIndex(BOOK_ISBN));
        book.Author = c.getString(c.getColumnIndex(BOOK_AUTHOR));
        book.PublicationYear = c.getString(c.getColumnIndex(BOOK_PUBLISH_YEAR));
        book.Description = c.getString(c.getColumnIndex(BOOK_DESCRIPTION));
        book.PageCount = c.getInt(c.getColumnIndex(BOOK_PAGE_COUNT));
        book.Status = c.getString(c.getColumnIndex(BOOK_STATUS));
        book.RentDuration = c.getInt(c.getColumnIndex(BOOK_RENT_DURATION));
        book.RentedTime = new Timestamp(c.getLong(c.getColumnIndex(BOOK_RENTED_TIME)));
        book.RentInformation = c.getString(c.getColumnIndex(BOOK_RENT_INFO));
        return book;
    }

    @SuppressLint("Range")
    private Request ToRequest(Cursor c) {
        Request request = new Request();
        request.Id = c.getLong(c.getColumnIndex(REQUEST_ID));
        request.RequesterId = c.getLong(c.getColumnIndex(REQUESTER_ID));
        request.BookId = c.getLong(c.getColumnIndex(REQUEST_BOOK_ID));
        request.RequestTimeStamp = new Timestamp(c.getLong(c.getColumnIndex(REQUEST_TIMESTAMP)));
        request.HasCompleted = Boolean.valueOf(c.getString(c.getColumnIndex(HAS_COMPLETED)));
        return request;
    }

    @SuppressLint("Range")
    private Message ToMessage(Cursor c) {
        Message message = new Message();
        message.Id = c.getLong(c.getColumnIndex(MESSAGE_ID));
        message.SenderId = c.getLong(c.getColumnIndex(MESSAGE_SENDER_ID));
        message.ReceiverId = c.getLong(c.getColumnIndex(MESSAGE_RECEIVER_ID));
        message.FromSystem = c.getInt(c.getColumnIndex(MESSAGE_FROM_SYSTEM))!=0;
        message.Content = c.getString(c.getColumnIndex(MESSAGE_CONTENT));
        message.TimeStamp = new Timestamp(c.getLong(c.getColumnIndex(MESSAGE_TIMESTAMP)));
        return message;
    }

    private void PopulateData(SQLiteDatabase db) {
        addUserRecord(new User(0l,"Admin", User.ROLE_ADMIN, "1111", "02 Crest Line Point","a3gr5d","7784561235","ccamelli0@wufoo.com"), db);
        addUserRecord(new User(0l, "Bruce", User.ROLE_USER, "1234","36851 Sunbrook Center", "a5dy1f", "778465151", "cdunhill1@blinklist.com"), db);
        addUserRecord(new User(0l, "Edward", User.ROLE_USER, "1234","425 Orin Circle", "r4xe4d", "6041114567", "lshoemark2@furl.net"), db);
        addUserRecord(new User(0l, "Barbara", User.ROLE_USER, "1234","5 Havey Road", "e5ga6r", "6045451133", "ldebischop3@xinhuanet.com"), db);

        addBookRecord(new Book(0l,"The Great Gatsby", 1l,1l,"12343264","F. Scott Fitzgerald", "1925","The Great Gatsby is a 1925 novel by American writer F. Scott Fitzgerald. Set in the Jazz Age on Long Island, near New York City, the novel depicts first-person narrator Nick Carraway's interactions with mysterious millionaire Jay Gatsby and Gatsby's obsession to reunite with his former lover, Daisy Buchanan.", 328, Book.STATUS_ACTIVE), db);
        addBookRecord(new Book(0l,"Pride and Prejudice", 1l,1l,"44654512365","Jane Austen", "1832","The Pride of Jane Austen! The story is set in England in the late 18th century.", 210, Book.STATUS_ACTIVE), db);
        addBookRecord(new Book(0l,"Lord of the Flies", 1l,1l,"122213213","William Golding", "1954","Lord of the Flies is a 1954 debut novel by Nobel Prize-winning British author William Golding.", 254, Book.STATUS_ACTIVE), db);
        addBookRecord(new Book(0l,"Fahrenheit 451", 2l,2l,"12354543","Ray Bradbury", "1953","Fahrenheit 451 is a 1953 dystopian novel by American writer Ray Bradbury. Often regarded as one of his best works, the novel presents a future American society where books are outlawed and \"firemen\" burn any that are found.", 256, Book.STATUS_FOR_RENT), db);
        addBookRecord(new Book(0l,"The Count of Monte Cristo", 2l,2l,"98656532","Alexandre Dumas", "1844","The Count of Monte Cristo is an adventure novel written by French author Alexandre Dumas completed in 1844.", 421, Book.STATUS_FOR_RENT), db);
    }
}
