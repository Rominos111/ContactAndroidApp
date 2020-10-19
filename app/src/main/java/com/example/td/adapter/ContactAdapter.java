package com.example.td.adapter;

import com.example.td.R;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.td.model.Contact;
import com.example.td.storage.ContactStorage;

public abstract class ContactAdapter extends RecyclerView.Adapter<ContactAdapter.ContactHolder> {
    static class ContactHolder extends RecyclerView.ViewHolder {
        private ImageView photo;
        private TextView name;

        public ContactHolder(@NonNull View itemView) {
            super(itemView);
            photo = itemView.findViewById(R.id.contact_photo_item);
            name = itemView.findViewById(R.id.contact_name_item);
        }

        public ImageView getPhoto() {
            return photo;
        }

        public TextView getName() {
            return name;
        }
    }

    private Context context;

    public ContactAdapter(Context context) {
        this.context = context;
    }

    @NonNull
    @Override
    public ContactHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                                  .inflate(R.layout.item_list_contact, parent, false);

        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ContactAdapter.this.onItemClick(v);
            }
        });

        view.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                return ContactAdapter.this.onItemLongClick(v);
            }
        });

        return new ContactHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ContactHolder holder, int position) {
        Contact contact = ContactStorage.get(context).findAll().get(position);
        holder.itemView.setTag(contact.getId());
        holder.name.setText(contact.getFirstName());
    }

    @Override
    public int getItemCount() {
        return ContactStorage.get(context).size();
    }

    public abstract void onItemClick(View v);

    public abstract boolean onItemLongClick(View v);
}
