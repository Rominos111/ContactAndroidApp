package com.example.td.storage.utility;

import android.content.Context;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public abstract class JSONFileStorage<T> extends FileStorage<T> {
    private final static String NEXT_ID = "next_id";
    private final static String LIST = "list";
    private final static String EXTENSION = "json";

    protected JSONObject json;

    public JSONFileStorage(Context context, String name) {
        super(context, name, EXTENSION);
    }

    protected abstract T jsonObjectToObject(JSONObject jsonObject);

    protected abstract JSONObject objectToJsonObject(int id, T object);

    @Override
    public T find(int id) {
        T object = null;

        try {
            object = jsonObjectToObject(json.getJSONObject(LIST).getJSONObject(String.valueOf(id)));
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return object;
    }

    @Override
    public List<T> findAll() {
        ArrayList<T> list = new ArrayList<>();

        try {
            Iterator<String> iterator = json.getJSONObject(LIST).keys();

            while (iterator.hasNext()) {
                list.add(jsonObjectToObject(json.getJSONObject(LIST).getJSONObject(iterator.next())));
            }
        }
        catch (JSONException e) {
            e.printStackTrace();
            list = null;
        }

        return list;
    }

    @Override
    public int size() {
        int size = 0;

        try {
            size = json.getJSONObject(LIST).length();
        }
        catch (JSONException e) {
            e.printStackTrace();
        }

        return size;
    }

    @Override
    public boolean insert(T elem) {
        int nextId = json.optInt(NEXT_ID);
        boolean ok = false;

        try {
            json.getJSONObject(LIST).put(String.valueOf(nextId), objectToJsonObject(nextId, elem));
            json.put(NEXT_ID, nextId + 1);
            ok = true;
        }
        catch (JSONException e) {
            e.printStackTrace();
        }

        write();
        return ok;
    }

    @Override
    public boolean update(int id, T elem) {
        boolean ok = false;

        try {
            json.getJSONObject(LIST).put(String.valueOf(id), objectToJsonObject(id, elem));
            ok = true;
        }
        catch (JSONException e) {
            e.printStackTrace();
        }

        write();
        return ok;
    }

    @Override
    public boolean delete(int id) {
        boolean ok = false;

        try {
            json.getJSONObject(LIST).remove(String.valueOf(id));
            ok = true;
        }
        catch (JSONException e) {
            e.printStackTrace();
        }
        write();

        return ok;
    }

    @Override
    protected void create() {
        json = new JSONObject();

        try {
            json.put(LIST, new JSONObject());
            json.put(NEXT_ID, 1);
        }
        catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void initialize(String value) {
        try {
            json = new JSONObject(value);
        }
        catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected String getValue() {
        return json.toString();
    }
}
