package com.example.td.storage.utility;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.BaseColumns;

import java.util.ArrayList;
import java.util.List;

public abstract class DataBaseStorage<T> implements Storage<T> {
    private SQLiteOpenHelper helper;
    private String table;

    public DataBaseStorage(SQLiteOpenHelper helper, String table) {
        this.helper = helper;
        this.table = table;
    }

    protected abstract ContentValues objectToContentValues(int id, T object);

    protected abstract T cursorToObject(Cursor cursor);

    @Override
    public boolean insert(T object) {
        long id = helper.getWritableDatabase().insert(
                table,
                null,
                objectToContentValues(-1, object)
        );
        return (id != -1);
    }

    @Override
    public List<T> findAll() {
        List<T> list = new ArrayList<>();
        Cursor cursor = helper.getReadableDatabase().query(
                table,
                null,
                null,
                null,
                null,
                null,
                null
        );
        while (cursor.moveToNext()) {
            list.add(cursorToObject(cursor));
        }
        cursor.close();
        return list;
    }

    @Override
    public T find(int id) {
        T object = null;
        Cursor cursor = helper.getReadableDatabase().query(
                table,
                null,
                BaseColumns._ID + " = ?",
                new String[]{"" + id},
                null,
                null,
                null
        );
        if (cursor.moveToNext()) {
            object = cursorToObject(cursor);
        }
        cursor.close();
        return object;
    }

    @Override
    public int size() {
        Cursor cursor = helper.getReadableDatabase().query(
                table,
                null,
                null,
                null,
                null,
                null,
                null
        );
        int nb = cursor.getCount();
        cursor.close();
        return nb;
    }

    public boolean update(int id, T object) {
        int nb = helper.getWritableDatabase().update(
                table,
                objectToContentValues(id, object),
                BaseColumns._ID + " = ?",
                new String[]{"" + id}
        );
        return (nb != 0);
    }

    @Override
    public boolean delete(int id) {
        int nb = helper.getWritableDatabase().delete(table, BaseColumns._ID + " = ?", new String[]{"" + id});
        return (nb != 0);
    }
}
