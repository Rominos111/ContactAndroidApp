package com.example.td.activity;

import com.example.td.R;
import com.example.td.adapter.ContactAdapter;
import com.example.td.model.Contact;

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);

        contacts = new ArrayList<>();
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
                Toast.makeText(getApplicationContext(), getString(R.string.description_contact_get_infos), Toast.LENGTH_SHORT).show();
                return false;
            }
        });
    }
}
