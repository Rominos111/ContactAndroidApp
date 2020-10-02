package com.example.td.activity;

import com.example.td.R;
import com.example.td.adapter.ContactAdapter;
import com.example.td.model.Contact;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class ListActivity extends AppCompatActivity {
    private List<Contact> contacts;
    private RecyclerView list;

    public static final String EXTRA_CONTACT = "contact";
    public static final String EXTRA_CREATE_CONTACT = "create_contact";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);

        contacts = new ArrayList<>();

        final Contact contact = (Contact) getIntent().getSerializableExtra(ListActivity.EXTRA_CREATE_CONTACT);

        if (contact != null) {
            contacts.add(contact);
        }

        contacts.add(new Contact("a", "A"));
        contacts.add(new Contact("b", "B"));
        contacts.add(new Contact("c", "C"));

        list = findViewById(R.id.contact_list);
        list.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        list.setAdapter(new ContactAdapter(contacts) {
            @Override
            public void onItemClick(View v) {
                Contact contact = contacts.get(list.getChildViewHolder(v).getAdapterPosition());
                Intent intent = new Intent(getApplicationContext(), ContactActivity.class);
                intent.putExtra(EXTRA_CONTACT, contact);
                startActivity(intent);
            }

            @Override
            public boolean onItemLongClick(View v) {
                contacts.remove(list.getChildViewHolder(v).getAdapterPosition());
                Toast.makeText(getApplicationContext(), getString(R.string.description_contact_deleted), Toast.LENGTH_SHORT).show();
                this.notifyDataSetChanged();
                return false;
            }
        });

        FloatingActionButton addContactButton = findViewById(R.id.button_add_contact);
        addContactButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), CreateContactActivity.class));
            }
        });
    }
}
