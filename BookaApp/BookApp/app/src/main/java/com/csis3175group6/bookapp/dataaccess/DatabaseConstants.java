package com.csis3175group6.bookapp.dataaccess;

import com.csis3175group6.bookapp.entities.Book;

public class DatabaseConstants {
    static final String BOOK_TABLE = Book.class.getSimpleName();
    static final String BOOK_COL_ID = "id";
    static final String BOOK_COL_TITLE = "title";
    static final String BOOK_COL_OWNER_ID = "owner_id";
    static final String BOOK_COL_HOLDER_ID ="holder_id";
    static final String BOOK_COL_ISBN = "isbn";
    static final String BOOK_COL_AUTHOR = "author";
    static final String BOOK_COL_PUBLICATION_YEAR = "publication_year";
    static final String BOOK_COL_STATUS = "status";
    static final String BOOK_COL_RENT_PRICE = "rent_price";
    static final String BOOK_COL_DESCRIPTION = "description";
    static final String BOOK_COL_PAGE_COUNT = "page_count";
}
