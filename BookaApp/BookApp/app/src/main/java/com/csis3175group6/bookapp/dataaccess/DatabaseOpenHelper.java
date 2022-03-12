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
import java.util.List;

public class DatabaseOpenHelper extends OrmLiteSqliteOpenHelper {

    final static String DATABASE_NAME = "BookManagement.db";
    final static int VERSION = 1;

    public DatabaseOpenHelper(Context context) {
        super(context, DATABASE_NAME, null, VERSION, R.raw.ormlite_config2);
    }

    @Override
    public void onCreate(SQLiteDatabase database, ConnectionSource connectionSource) {
        try {
            TableUtils.createTable(connectionSource, User.class);
            TableUtils.createTable(connectionSource, Book.class);
            TableUtils.createTable(connectionSource, ReadHistory.class);
            TableUtils.createTable(connectionSource, Message.class);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase database, ConnectionSource connectionSource, int oldVersion, int newVersion) {
        try {
            TableUtils.dropTable(connectionSource, Message.class, true);
            TableUtils.dropTable(connectionSource, ReadHistory.class, true);
            TableUtils.dropTable(connectionSource, Book.class, true);
            TableUtils.dropTable(connectionSource, User.class, true);
            onCreate(database, connectionSource);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public boolean insertBook(Book book){
        try {
            Dao<Book, Long> bookDao = getDao(Book.class);
            int affectedRows = bookDao.create(book);
            return affectedRows > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean updateBook(Book book) {

        try {
            Dao<Book, Long> bookDao = getDao(Book.class);
            int affectedRows = bookDao.update(book);
            return affectedRows > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public List<Book> getBooksByOwnerId(Long ownerId){
        try {
            Dao<Book, Long> bookDao = getDao(Book.class);
            List<Book> books = bookDao.queryBuilder().where().eq("ownerId", ownerId).query();
            return books;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public List<Book> getBooksByHolderId(Long holderId){
        try {
            Dao<Book, Long> bookDao = getDao(Book.class);
            List<Book> books = bookDao.queryBuilder().where().eq("holderId", holderId).query();
            return books;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public List<User> getUsers() {
        try {
            Dao<User, Long> userDao = getDao(User.class);
            return userDao.queryForAll();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public User getUserByCredential(String name, String pinCode) {
        try {
            Dao<User, Long> userDao = getDao(User.class);
            User user = userDao.queryBuilder().where().eq("Name", name).and().eq("pinCode", pinCode).queryForFirst();
            return user;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public boolean updateUser(User user){
        try {
            Dao<User, Long> userDao = getDao(User.class);
            int affectedRows = userDao.update(user);
            return affectedRows > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
}
