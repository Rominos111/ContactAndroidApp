package com.example.td.storage;

import android.content.Context;
import android.content.SharedPreferences;

import com.example.td.model.Contact;
import com.example.td.storage.utility.Storage;

public final class ContactStorage {
    private static final String PREF_NAME = "com.example.td.preferences";
    private static final String PREF_STORAGE = "storage";

    public static final int PREF_STORAGE_NONE = 0;
    public static final int PREF_STORAGE_FILE_JSON = 1;
    public static final int PREF_STORAGE_DATABASE = 2;

    private static final int PREF_STORAGE_DEFAULT = PREF_STORAGE_NONE;

    private static SharedPreferences getPreferences(Context context) {
        return context.getSharedPreferences(PREF_STORAGE, PREF_STORAGE_DEFAULT);
    }

    public static int getPreferencesStorage(Context context) {
        return getPreferences(context).getInt(PREF_STORAGE, PREF_STORAGE_DEFAULT);
    }

    public static void setPreferencesStorage(Context context, int prefStorage) {
        getPreferences(context).edit().putInt(PREF_STORAGE, prefStorage).apply();
    }

    public static Storage<Contact> get(Context context) {
        SharedPreferences preferences = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        switch (preferences.getInt(PREF_STORAGE, PREF_STORAGE_DEFAULT)) {
            case PREF_STORAGE_FILE_JSON:
                return ContactJsonFileStorage.get(context);

            case PREF_STORAGE_DATABASE:
                return ContactDataBaseStorage.get(context);
        }

        return ContactNoneStorage.get();
    }
}
