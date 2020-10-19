package com.example.td.activity;

import com.example.td.R;
import com.example.td.adapter.ContactAdapter;
import com.example.td.dialog.ContactDialogFragment;
import com.example.td.dialog.DeleteDialogFragment;
import com.example.td.dialog.Updatable;
import com.example.td.model.Contact;
import com.example.td.storage.ContactStorage;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

public class ListActivity extends AppCompatActivity implements Updatable {
    // TODO: Supprimer ce truc ?
    private static List<Contact> contacts = new ArrayList<>();

    private RecyclerView list;

    public static final String EXTRA_CONTACT = "contact";
    public static final String EXTRA_CREATE_CONTACT = "create_contact";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);

        final Contact contact = (Contact) getIntent().getSerializableExtra(ListActivity.EXTRA_CREATE_CONTACT);

        if (contact != null) {
            contacts.add(contact);
        }

        list = findViewById(R.id.contact_list);
        list.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        list.setAdapter(new ContactAdapter(getApplicationContext()) {
            @Override
            public void onItemClick(View v) {
                Contact contact = contacts.get(list.getChildViewHolder(v).getAdapterPosition());
                Intent intent = new Intent(getApplicationContext(), ContactActivity.class);
                intent.putExtra(EXTRA_CONTACT, contact);
                startActivity(intent);
            }

            @Override
            public boolean onItemLongClick(View v) {
                new DeleteDialogFragment(ListActivity.this, (int) v.getTag()).show(getSupportFragmentManager(), "");
                return true;
            }
        });

        FloatingActionButton addContactButton = findViewById(R.id.button_add_contact);
        addContactButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new ContactDialogFragment(ListActivity.this).show(getSupportFragmentManager(), "");
            }
        });
    }

    @Override
    protected void onStart() {
        update();
        super.onStart();
    }

    @Override
    public void update() {
        list.getAdapter().notifyDataSetChanged();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.settings, menu);
        return true;
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        // Get, vue

        int id = R.id.storage_none;
        switch (ContactStorage.getPreferencesStorage(getApplicationContext())) {
            case R.id.storage_json:
                id = R.id.storage_json;
                break;

            case R.id.storage_database:
                id = R.id.storage_database;
                break;
        }

        menu.findItem(id).setChecked(true);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Set, action

        int prefStorage = ContactStorage.PREF_STORAGE_NONE;
        switch (item.getItemId()) {
            case R.id.storage_json:
                prefStorage = ContactStorage.PREF_STORAGE_FILE_JSON;
                break;

            case R.id.storage_database:
                prefStorage = ContactStorage.PREF_STORAGE_DATABASE;
                break;
        }
        ContactStorage.setPreferencesStorage(getApplicationContext(), prefStorage);
        list.getAdapter().notifyDataSetChanged();
        return true;
    }
}
