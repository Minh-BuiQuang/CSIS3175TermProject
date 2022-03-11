package com.csis3175group6.bookapp.dataaccess;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import com.csis3175group6.bookapp.R;
import com.csis3175group6.bookapp.entities.*;
import com.j256.ormlite.android.apptools.OrmLiteSqliteOpenHelper;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;
import java.sql.SQLException;

public class DatabaseOpenHelper extends OrmLiteSqliteOpenHelper {

    final static String DATABASE_NAME = "BookManagement.db";
    final static int VERSION = 1;
    private Dao<Book, Long> BookDao;
    private Dao<User, Long> UserDao;

    public DatabaseOpenHelper(Context context) {
        super(context, DATABASE_NAME, null, VERSION, R.raw.ormlite_config2);
    }

    @Override
    public void onCreate(SQLiteDatabase database, ConnectionSource connectionSource) {
        try {
            TableUtils.createTable(connectionSource, User.class);
            TableUtils.createTable(connectionSource, Book.class);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase database, ConnectionSource connectionSource, int oldVersion, int newVersion) {
        try {
            TableUtils.dropTable(connectionSource, User.class, true);
            TableUtils.dropTable(connectionSource, Book.class, true);
            onCreate(database, connectionSource);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
