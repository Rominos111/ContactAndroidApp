package com.example.td.activity;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.ColorStateList;
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

public class ContactActivity extends AppCompatActivity {
    private final int REQUEST_IMAGE_CAPTURE = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact);

        final Contact contact = (Contact) getIntent().getSerializableExtra(ListActivity.EXTRA_CONTACT);

        ((TextView) findViewById(R.id.contact_name)).setText(getString(R.string.contact_name, contact.getFirstName(), contact.getLastName()));
        ((TextView) findViewById(R.id.contact_phone_portable)).setText(contact.getPhonePortable());
        ((TextView) findViewById(R.id.contact_phone_home)).setText(contact.getPhoneHome());
        ((TextView) findViewById(R.id.contact_email)).setText(contact.getEmail());
        ((TextView) findViewById(R.id.contact_location)).setText(contact.getLocation());

        findViewById(R.id.button_call).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + contact.getPhonePortable()));

                if (ActivityCompat.checkSelfPermission(ContactActivity.this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                    Toast.makeText(ContactActivity.this, "Permission manquante", Toast.LENGTH_SHORT).show();
                }
                else {
                    startActivity(intent);
                }
            }
        });

        findViewById(R.id.button_call).setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                Toast.makeText(getApplicationContext(), getString(R.string.description_call_contact), Toast.LENGTH_SHORT).show();
                return false;
            }
        });

        findViewById(R.id.button_sms).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("smsto:" + contact.getPhonePortable()));
                        // .setType("vnd.android-dir/mms-sms");

                if (intent.resolveActivity(getPackageManager()) == null) {
                    Toast.makeText(getApplicationContext(), getString(R.string.error_send_message), Toast.LENGTH_SHORT).show();
                }
                else {
                    startActivity(intent);
                }
            }
        });

        findViewById(R.id.button_sms).setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                Toast.makeText(getApplicationContext(), getString(R.string.description_send_sms), Toast.LENGTH_SHORT).show();
                return false;
            }
        });

        findViewById(R.id.button_mail).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_SENDTO, Uri.parse("mailto:" + contact.getEmail()));
                Intent chooser = Intent.createChooser(intent, getString(R.string.contact_send_mail_detail));
                // Liste des applis mail
                startActivity(chooser);
            }
        });

        findViewById(R.id.button_mail).setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                Toast.makeText(getApplicationContext(), getString(R.string.description_send_mail), Toast.LENGTH_SHORT).show();
                return false;
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

        findViewById(R.id.contact_photo).setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                Toast.makeText(getApplicationContext(), getString(R.string.description_choose_photo), Toast.LENGTH_SHORT).show();
                return false;
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            if (data != null && data.getExtras() != null) {
                ImageView image = findViewById(R.id.contact_photo);
                image.setImageBitmap((Bitmap) data.getExtras().get("data"));
                image.setImageTintList(new ColorStateList(new int[0][0], new int[0]));
            }
        }
    }
}
