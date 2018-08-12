/*
 * Copyright (C) 2016 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.example.android.bookstore2.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Database helper for Bookstore app. Manages database creation and version management.
 */
public class BookDbHelper extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "bookstore.db";

    public BookDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    private static final String INTEGER_TYPE = " INTEGER";
    private static final String ID_ATTRIBUTES = " PRIMARY KEY AUTOINCREMENT";
    private static final String TEXT_TYPE = " TEXT";
    private static final String NOT_NULL = " NOT NULL";
    private static final String DEFAULT = " DEFAULT ";
    private static final String COMMA_SEP = ", ";


    @Override
    public void onCreate(SQLiteDatabase db) {
        String SQL_CREATE_BOOKS_TABLE = "CREATE TABLE " + BookContract.BookEntry.TABLE_NAME + "("
                + BookContract.BookEntry._ID + INTEGER_TYPE + ID_ATTRIBUTES + COMMA_SEP
                + BookContract.BookEntry.COLUMN_BOOK_NAME + TEXT_TYPE + NOT_NULL + COMMA_SEP
                + BookContract.BookEntry.COLUMN_BOOK_AUTHOR + TEXT_TYPE + NOT_NULL + COMMA_SEP
                + BookContract.BookEntry.COLUMN_BOOK_PRICE + INTEGER_TYPE + NOT_NULL + DEFAULT + "0" + COMMA_SEP
                + BookContract.BookEntry.COLUMN_BOOK_QUANTITY + INTEGER_TYPE + NOT_NULL + DEFAULT + "0" + COMMA_SEP
                + BookContract.BookEntry.COLUMN_BOOK_SUPPLIER_NAME + TEXT_TYPE + COMMA_SEP
                + BookContract.BookEntry.COLUMN_BOOK_SUPPLIER_PHONE_NR + TEXT_TYPE + NOT_NULL + ")";

        db.execSQL(SQL_CREATE_BOOKS_TABLE);
        Log.v("CatalogActivity", "Table created: " + SQL_CREATE_BOOKS_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
