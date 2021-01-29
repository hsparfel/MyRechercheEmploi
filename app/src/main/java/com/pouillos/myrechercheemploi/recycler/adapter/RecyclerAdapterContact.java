package com.pouillos.myrechercheemploi.recycler.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.recyclerview.widget.RecyclerView;

import com.pouillos.myrechercheemploi.R;
import com.pouillos.myrechercheemploi.entities.Contact;
import com.pouillos.myrechercheemploi.recycler.holder.RecyclerViewHolderContact;

import java.util.List;

public class RecyclerAdapterContact extends RecyclerView.Adapter<RecyclerViewHolderContact> {

        private List<Contact> listContact;

    public interface Listener {
        void onClickContactButton(int position);
    }

    private final Listener callback;


    public RecyclerAdapterContact(List<Contact> listContact, Listener callback) {
        this.listContact = listContact;
        this.callback = callback;
    }

        @Override
        public RecyclerViewHolderContact onCreateViewHolder(ViewGroup parent, int viewType) {
            Context context = parent.getContext();
            LayoutInflater inflater = LayoutInflater.from(context);
            View view = inflater.inflate(R.layout.recycler_list_contact, parent, false);

            return new RecyclerViewHolderContact(view);
        }

        @Override
        public void onBindViewHolder(RecyclerViewHolderContact viewHolder, int position) {
            viewHolder.updateWithContact(this.listContact.get(position),this.callback);
        }

        @Override
        public int getItemCount() {
            return this.listContact.size();
        }

    public Contact getContact(int position){
        return this.listContact.get(position);
    }

}
