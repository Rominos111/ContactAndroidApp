package com.example.td.storage;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.BaseColumns;

import com.example.td.model.Contact;
import com.example.td.storage.utility.DataBaseStorage;

public class ContactDataBaseStorage extends DataBaseStorage<Contact> {
    private static final String TABLE_NAME = "contact";
    private static final int NUM_ID = 0;

    private static final String NAME_FIRST_NAME = "firstName";
    private static final int NUM_FIRST_NAME = 1;

    private static final String NAME_LAST_NAME = "lastName";
    private static final int NUM_LAST_NAME = 2;

    private static final String NAME_PHONE_PORTABLE = "phonePortable";
    private static final int NUM_PHONE_PORTABLE = 3;

    private static final String NAME_PHONE_HOME = "phoneHome";
    private static final int NUM_PHONE_HOME = 4;

    private static final String NAME_EMAIL = "email";
    private static final int NUM_EMAIL = 5;

    private static final String NAME_LOCATION = "location";
    private static final int NUM_LOCATION = 6;

    private static ContactDataBaseStorage storage;

    public static ContactDataBaseStorage get(Context context) {
        if (storage == null) {
            storage = new ContactDataBaseStorage(context);
        }

        return storage;
    }

    private static class DataBaseHelper extends SQLiteOpenHelper {
        private static final int DATABASE_VERSION = 1;
        private static final String DATABASE_NAME = "Contact.db";

        private static final String SQL_CREATE_CONTACT_ENTRIES =
                "CREATE TABLE " + TABLE_NAME + " ("
                + BaseColumns._ID + " INTEGER PRIMARY KEY, "
                + NAME_FIRST_NAME + " TEXT, "
                + NAME_LAST_NAME + " TEXT, "
                + NAME_PHONE_PORTABLE + " TEXT, "
                + NAME_PHONE_HOME + " TEXT, "
                + NAME_EMAIL + " TEXT, "
                + NAME_LOCATION + " TEXT)";

        public DataBaseHelper(Context context) {
            super(context, DATABASE_NAME, null, DATABASE_VERSION);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            db.execSQL(SQL_CREATE_CONTACT_ENTRIES);
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            // À compléter si upgrade BDD
        }
    }

    private ContactDataBaseStorage(Context context) {
        super(new DataBaseHelper(context), TABLE_NAME);
    }

    @Override
    protected ContentValues objectToContentValues(int id, Contact object) {
        ContentValues values = new ContentValues();
        values.put(NAME_FIRST_NAME, object.getFirstName());
        values.put(NAME_LAST_NAME, object.getLastName());
        values.put(NAME_PHONE_PORTABLE, object.getPhonePortable());
        values.put(NAME_PHONE_HOME, object.getPhoneHome());
        values.put(NAME_EMAIL, object.getEmail());
        values.put(NAME_LOCATION, object.getLocation());
        return values;
    }

    @Override
    protected Contact cursorToObject(Cursor cursor) {
        return new Contact(
                cursor.getInt(NUM_ID),
                cursor.getString(NUM_FIRST_NAME),
                cursor.getString(NUM_LAST_NAME),
                cursor.getString(NUM_PHONE_PORTABLE),
                cursor.getString(NUM_PHONE_HOME),
                cursor.getString(NUM_EMAIL),
                cursor.getString(NUM_LOCATION)
        );
    }
}
