package com.example.td.activity;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.td.R;
import com.example.td.model.Contact;

public class MainActivity extends AppCompatActivity {
    private final int REQUEST_IMAGE_CAPTURE = 1;

    private Contact contact;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        contact = new Contact(
                "a",
                "b",
                "06 01 02 03 04",
                "04 01 02 03 04",
                "test@example.com",
                "Paris");

        ((TextView) findViewById(R.id.contact_name)).setText(getString(R.string.contact_name, contact.getFirstName(), contact.getLastName()));
        ((TextView) findViewById(R.id.contact_phone_portable)).setText(contact.getPhonePortable());
        ((TextView) findViewById(R.id.contact_phone_home)).setText(contact.getPhoneHome());
        ((TextView) findViewById(R.id.contact_email)).setText(contact.getEmail());
        ((TextView) findViewById(R.id.contact_location)).setText(contact.getLocation());

        findViewById(R.id.button_call).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + contact.getPhonePortable()));
                startActivity(intent);
            }
        });

        findViewById(R.id.button_sms).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("smsto:" + contact.getPhonePortable()))
                        .setType("vnd.android-dir/mms-sms");

                if (intent.resolveActivity(getPackageManager()) == null) {
                    // Toast.makeText(MainActivity.this, "OK", Toast.LENGTH_SHORT).show();
                    Toast.makeText(getApplicationContext(), getString(R.string.error_send_message), Toast.LENGTH_SHORT).show();
                }
                else {
                    startActivity(intent);
                }
            }
        });

        findViewById(R.id.button_sms).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_SENDTO, Uri.parse("mailto:" + contact.getEmail()));
                Intent chooser = Intent.createChooser(intent, getString(R.string.contact_send_mail_detail));
                // Liste des applis mail
                startActivity(chooser);
            }
        });

        findViewById(R.id.contact_photo).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                // v1 : Aperçu sans sauvegarde
                startActivityForResult(intent, REQUEST_IMAGE_CAPTURE);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            ((ImageView) findViewById(R.id.contact_photo)).setImageBitmap((Bitmap) data.getExtras().get("data"));
        }
    }
}
