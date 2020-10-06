package com.example.td.storage;

import android.content.Context;

import org.json.JSONException;
import org.json.JSONObject;

import com.example.td.model.Contact;
import com.example.td.storage.utility.JSONFileStorage;

public class ContactJsonFileStorage extends JSONFileStorage<Contact> {
    private static final String NAME = "contact";
    private static final String CONTACT_ID = "id";
    private static final String CONTACT_FIRST_NAME = "first_name";
    private static final String CONTACT_LAST_NAME = "last_name";
    private static final String CONTACT_PHONE_PORTABLE = "phone_portable";
    private static final String CONTACT_PHONE_HOME = "phone_home";
    private static final String CONTACT_EMAIL = "email";
    private static final String CONTACT_LOCATION = "location";

    public ContactJsonFileStorage(Context context, String name) {
        super(context, name);
    }

    @Override
    protected JSONObject objectToJsonObject(int id, Contact object) {
        JSONObject json = new JSONObject();

        try {
            json.put(CONTACT_ID, id);
            json.put(CONTACT_FIRST_NAME, object.getFirstName());
            json.put(CONTACT_LAST_NAME, object.getLastName());
            json.put(CONTACT_PHONE_PORTABLE, object.getPhonePortable());
            json.put(CONTACT_PHONE_HOME, object.getPhoneHome());
            json.put(CONTACT_EMAIL, object.getEmail());
            json.put(CONTACT_LOCATION, object.getLocation());
        }
        catch (JSONException e) {
            e.printStackTrace();
            json = null;
        }

        return json;
    }

    @Override
    protected Contact jsonObjectToObject(JSONObject jsonObject) {
        try {
            return new Contact(jsonObject.getString(CONTACT_FIRST_NAME),
                    jsonObject.getString(CONTACT_LAST_NAME),
                    jsonObject.getString(CONTACT_PHONE_PORTABLE),
                    jsonObject.getString(CONTACT_PHONE_HOME),
                    jsonObject.getString(CONTACT_EMAIL),
                    jsonObject.getString(CONTACT_LOCATION));
        }
        catch (JSONException e) {
            e.printStackTrace();
            return null;
        }
    }
}
