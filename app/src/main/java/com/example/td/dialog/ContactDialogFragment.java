package com.example.td.dialog;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.example.td.R;
import com.example.td.model.Contact;
import com.example.td.storage.ContactStorage;

public class ContactDialogFragment extends DialogFragment {
    private static final int ID_NONE = -1;

    private Updatable updatable;
    private int id;
    private View view;

    public ContactDialogFragment(Updatable updatable) {
        this(updatable, ID_NONE);
    }

    public ContactDialogFragment(Updatable updatable, int id) {
        this.updatable = updatable;
        this.id = id;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        view = requireActivity().getLayoutInflater().inflate(R.layout.dialog_contact, null);
        setContactToView();
        return new AlertDialog.Builder(getActivity())
                .setTitle("Modifier un contact")
                .setView(view)
                .setPositiveButton("Valider", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Contact contact = getContactFromView();
                        if (ContactDialogFragment.this.id == ID_NONE) {
                            ContactStorage.get(getContext()).insert(contact);
                        }
                        else {
                            ContactStorage.get(getContext()).update(ContactDialogFragment.this.id, contact);
                        }
                        updatable.update();
                    }
                })
                .setNegativeButton("Annuler", null)
                .create();
    }

    private void setContactToView() {
        if (id == ID_NONE) {
            return;
        }

        Contact contact = ContactStorage.get(getContext()).find(id);

        if (contact != null) {
            ((EditText) view.findViewById(R.id.create_contact_first_name)).setText(contact.getFirstName());
            ((EditText) view.findViewById(R.id.create_contact_last_name)).setText(contact.getLastName());
            ((EditText) view.findViewById(R.id.create_contact_phone_mobile)).setText(contact.getPhonePortable());
            ((EditText) view.findViewById(R.id.create_contact_phone_home)).setText(contact.getPhoneHome());
            ((EditText) view.findViewById(R.id.create_contact_mail)).setText(contact.getEmail());
            ((EditText) view.findViewById(R.id.create_contact_location)).setText(contact.getLocation());
        }
    }

    private Contact getContactFromView() {
        return new Contact(
                id,
                ((EditText) view.findViewById(R.id.create_contact_first_name)).getText().toString(),
                ((EditText) view.findViewById(R.id.create_contact_last_name)).getText().toString(),
                ((EditText) view.findViewById(R.id.create_contact_phone_mobile)).getText().toString(),
                ((EditText) view.findViewById(R.id.create_contact_phone_home)).getText().toString(),
                ((EditText) view.findViewById(R.id.create_contact_mail)).getText().toString(),
                ((EditText) view.findViewById(R.id.create_contact_location)).getText().toString()
        );
    }
}
