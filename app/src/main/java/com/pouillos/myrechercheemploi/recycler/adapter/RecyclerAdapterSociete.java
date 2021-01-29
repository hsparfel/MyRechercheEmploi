package com.pouillos.myrechercheemploi.recycler.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.recyclerview.widget.RecyclerView;

import com.pouillos.myrechercheemploi.R;
import com.pouillos.myrechercheemploi.entities.Contact;
import com.pouillos.myrechercheemploi.entities.Societe;
import com.pouillos.myrechercheemploi.recycler.holder.RecyclerViewHolderContact;
import com.pouillos.myrechercheemploi.recycler.holder.RecyclerViewHolderSociete;

import java.util.List;

public class RecyclerAdapterSociete extends RecyclerView.Adapter<RecyclerViewHolderSociete> {

        private List<Societe> listSociete;

    public interface Listener {
        void onClickSocieteButton(int position);
    }

    private final Listener callback;


    public RecyclerAdapterSociete(List<Societe> listSociete, Listener callback) {
        this.listSociete = listSociete;
        this.callback = callback;
    }

        @Override
        public RecyclerViewHolderSociete onCreateViewHolder(ViewGroup parent, int viewType) {
            Context context = parent.getContext();
            LayoutInflater inflater = LayoutInflater.from(context);
            View view = inflater.inflate(R.layout.recycler_list_societe, parent, false);

            return new RecyclerViewHolderSociete(view);
        }

        @Override
        public void onBindViewHolder(RecyclerViewHolderSociete viewHolder, int position) {
            viewHolder.updateWithSociete(this.listSociete.get(position),this.callback);
        }

        @Override
        public int getItemCount() {
            return this.listSociete.size();
        }

    public Societe getSociete(int position){
        return this.listSociete.get(position);
    }

}
