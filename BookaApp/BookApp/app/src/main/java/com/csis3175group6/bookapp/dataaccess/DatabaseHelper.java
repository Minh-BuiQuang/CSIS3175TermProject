package com.csis3175group6.bookapp.dataaccess;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import androidx.annotation.Nullable;
import static com.csis3175group6.bookapp.dataaccess.DatabaseConstants.*;

public class DatabaseHelper extends SQLiteOpenHelper {

    final static String DATABASE_NAME = "BookManagement.db";
    final static int VERSION = 1;

    public DatabaseHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String query = "create table " + BOOK_TABLE + "("+
                BOOK_COL_ID +" integer primary key autoincrement," +
                BOOK_COL_TITLE +" text not null," +
                BOOK_COL_OWNER_ID +" integer not null," +
                BOOK_COL_HOLDER_ID +" integer not null," +
                BOOK_COL_ISBN +" integer," +
                BOOK_COL_AUTHOR +" text," +
                BOOK_COL_PUBLICATION_YEAR +" integer," +
                BOOK_COL_STATUS +" integer," +
                BOOK_COL_RENT_PRICE +" real," +
                BOOK_COL_DESCRIPTION +" text," +
                BOOK_COL_PAGE_COUNT +" integer not null);";
        db.execSQL(query);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL("drop table if exists " + BOOK_TABLE);
        onCreate(db);
    }
}
