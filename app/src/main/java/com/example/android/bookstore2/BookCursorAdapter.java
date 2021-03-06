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
package com.example.android.bookstore2;

import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CursorAdapter;
import android.widget.TextView;

import com.example.android.bookstore2.data.BookContract;

/**
 * {@link BookCursorAdapter} is an adapter for a list or grid view
 * that uses a {@link Cursor} of book data as its data source. This adapter knows
 * how to create list items for each row of book data in the {@link Cursor}.
 */
public class BookCursorAdapter extends CursorAdapter {
    public TextView nameTextView;
    public TextView authorTextView;
    public TextView priceTextView;
    public TextView quantityTextView;
    public Button addToCartButton;


    /**
     * Constructs a new {@link BookCursorAdapter}.
     *
     * @param context The context
     * @param c       The cursor from which to get the data.
     */

    public BookCursorAdapter(Context context, Cursor c) {
        super(context, c, 0);
    }

    /**
     * Makes a new blank list item view. No data is set (or bound) to the views yet.
     *
     * @param context app context
     * @param cursor  The cursor from which to get the data. The cursor is already
     *                moved to the correct position.
     * @param parent  The parent to which the new view is attached to
     * @return the newly created list item view.
     */
    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        // Inflate a list item view using the layout specified in list_item.xml
        return LayoutInflater.from(context).inflate(R.layout.list_item, parent, false);
    }

    /**
     * This method binds the book data (in the current row pointed to by cursor) to the given
     * list item layout.
     *
     * @param view    Existing view, returned earlier by newView() method
     * @param context app context
     * @param cursor  The cursor from which to get the data. The cursor is already moved to the
     *                correct row.
     */
    @Override
    public void bindView(View view, final Context context, final Cursor cursor) {
        // Find individual views that we want to modify in the list item layout
        nameTextView = (TextView) view.findViewById(R.id.name_text_view);
        authorTextView = (TextView) view.findViewById(R.id.author_text_view);
        priceTextView = (TextView) view.findViewById(R.id.price_text_view);
        quantityTextView = (TextView) view.findViewById(R.id.quantity_number_text_view);
        addToCartButton = (Button) view.findViewById(R.id.buy_button);

        // Find the columns of book attributes that we're interested in
        int nameColumnIndex = cursor.getColumnIndex(BookContract.BookEntry.COLUMN_BOOK_NAME);
        int authorColumnIndex = cursor.getColumnIndex(BookContract.BookEntry.COLUMN_BOOK_AUTHOR);
        int priceColumnIndex = cursor.getColumnIndex(BookContract.BookEntry.COLUMN_BOOK_PRICE);
        int quantityColumnIndex = cursor.getColumnIndex(BookContract.BookEntry.COLUMN_BOOK_QUANTITY);

        // Read the book attributes from the Cursor for the current book
        final Long bookId = cursor.getLong(cursor.getColumnIndexOrThrow(BookContract.BookEntry._ID));
        String bookName = cursor.getString(nameColumnIndex);
        String bookAuthor = cursor.getString(authorColumnIndex);
        double bookPriceDouble = cursor.getDouble(priceColumnIndex);
        int bookPriceInt = cursor.getInt(priceColumnIndex);
        final String bookQuantity = cursor.getString(quantityColumnIndex);

        if (Integer.parseInt(bookQuantity)>0){
            addToCartButton.setActivated(true);
        }else{
            addToCartButton.setActivated(false);
        }

        //Check price in order to show decimals only if not equal to 0
        String bookPrice;
        if (bookPriceDouble == bookPriceInt){
            bookPrice = Integer.toString(bookPriceInt) + "€";
        }else {
            bookPrice = Double.toString(bookPriceDouble) + "€";
        }

        //Add listener to the sale button
        addToCartButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int quantity = Integer.parseInt(bookQuantity) - 1;
                if (quantity < 0) {
                    addToCartButton.setActivated(false);
                } else {
                    ContentValues values = new ContentValues();
                    values.put(BookContract.BookEntry.COLUMN_BOOK_QUANTITY, quantity);
                    Uri currentBookUri = ContentUris.withAppendedId(BookContract.BookEntry.CONTENT_URI, bookId);
                    context.getContentResolver().update(currentBookUri, values, null, null);
                }
            }

        });

        // Update the TextViews with the attributes for the current book
        nameTextView.setText(bookName);
        authorTextView.setText(bookAuthor);
        quantityTextView.setText(bookQuantity);
        priceTextView.setText(bookPrice);
    }
}
