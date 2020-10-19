package com.example.td.storage;

import com.example.td.model.Contact;
import com.example.td.storage.utility.Storage;

import java.util.ArrayList;
import java.util.List;

public class ContactNoneStorage implements Storage<Contact> {
    private static ContactNoneStorage storage;

    public static ContactNoneStorage get() {
        if (storage == null) {
            storage = new ContactNoneStorage();
        }

        return storage;
    }

    public ContactNoneStorage() {

    }

    @Override
    public Contact find(int id) {
        return null;
    }

    @Override
    public List<Contact> findAll() {
        return new ArrayList<>();
    }

    @Override
    public int size() {
        return 0;
    }

    @Override
    public boolean insert(Contact elem) {
        return false;
    }

    @Override
    public boolean update(int id, Contact elem) {
        return false;
    }

    @Override
    public boolean delete(int id) {
        return false;
    }
}
