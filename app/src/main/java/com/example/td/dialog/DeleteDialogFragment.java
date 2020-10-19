package com.example.td.dialog;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.example.td.R;
import com.example.td.model.Contact;
import com.example.td.storage.ContactStorage;

public class DeleteDialogFragment extends DialogFragment {
    private Updatable updatable;
    private int id;

    public DeleteDialogFragment(Updatable updatable, int id) {
        this.updatable = updatable;
        this.id = id;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        Contact contact = ContactStorage.get(getContext()).find(id);
        return new AlertDialog.Builder(getActivity())
                .setTitle("Suppression")
                .setMessage(getString(R.string.dialog_delete_message, contact.getFirstName(), contact.getLastName()))
                .setPositiveButton("Valider", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        ContactStorage.get(getContext()).delete(DeleteDialogFragment.this.id);
                        updatable.update();
                    }
                })
                .setNegativeButton("Annuler", null)
                .create();
    }
}
