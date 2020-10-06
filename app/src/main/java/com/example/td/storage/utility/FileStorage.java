package com.example.td.storage.utility;

import android.content.Context;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

public abstract class FileStorage<T> implements Storage<T> {
    private static final String PREFIX = "storage_";

    private Context context;
    private String fileName;

    public FileStorage(Context context, String name, String extension) {
        this.context = context;
        this.fileName = PREFIX + name + "." + extension;
    }

    protected abstract void create();

    protected abstract void initialize(String value);

    protected abstract String getValue();

    protected void read() {
        try {
            FileInputStream in = context.openFileInput(fileName);
            System.out.println(context.getFilesDir());
            if (in != null) {
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(in));
                String tmp;
                StringBuilder builder = new StringBuilder();

                while ((tmp = bufferedReader.readLine()) != null) {
                    builder.append(tmp);
                }

                in.close();
                initialize(builder.toString());
            }
        }
        catch (FileNotFoundException e) {
            create();
            write();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    protected boolean write() {
        try {
            FileOutputStream out = context.openFileOutput(fileName, Context.MODE_PRIVATE);
            OutputStreamWriter writer = new OutputStreamWriter(out);
            writer.write(getValue());
            writer.close();
            return true;
        }
        catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}
