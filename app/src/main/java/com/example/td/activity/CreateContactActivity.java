package com.example.td.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.td.R;
import com.example.td.model.Contact;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

public class CreateContactActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_contact);

        findViewById(R.id.button_add_contact).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Contact contact = new Contact(
                        ((TextView) findViewById(R.id.create_contact_first_name)).getText().toString(),
                        ((TextView) findViewById(R.id.create_contact_last_name)).getText().toString(),
                        ((TextView) findViewById(R.id.create_contact_phone_mobile)).getText().toString(),
                        ((TextView) findViewById(R.id.create_contact_phone_home)).getText().toString(),
                        ((TextView) findViewById(R.id.create_contact_mail)).getText().toString(),
                        ((TextView) findViewById(R.id.create_contact_location)).getText().toString()
                );

                Intent intent = new Intent(getApplicationContext(), ListActivity.class);
                intent.putExtra(ListActivity.EXTRA_CREATE_CONTACT, contact);
                startActivity(intent);
            }
        });
    }
}
