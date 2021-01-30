package com.pouillos.myrechercheemploi.recycler.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.recyclerview.widget.RecyclerView;

import com.pouillos.myrechercheemploi.R;
import com.pouillos.myrechercheemploi.entities.Opportunite;
import com.pouillos.myrechercheemploi.recycler.holder.RecyclerViewHolderOpportunite;

import java.util.List;

public class RecyclerAdapterOpportunite extends RecyclerView.Adapter<RecyclerViewHolderOpportunite> {

        private List<Opportunite> listOpportunite;

    public interface Listener {
        void onClickOpportuniteButton(int position);
    }

    private final Listener callback;


    public RecyclerAdapterOpportunite(List<Opportunite> listOpportunite, Listener callback) {
        this.listOpportunite = listOpportunite;
        this.callback = callback;
    }

        @Override
        public RecyclerViewHolderOpportunite onCreateViewHolder(ViewGroup parent, int viewType) {
            Context context = parent.getContext();
            LayoutInflater inflater = LayoutInflater.from(context);
            View view = inflater.inflate(R.layout.recycler_list_opportunite, parent, false);

            return new RecyclerViewHolderOpportunite(view);
        }

        @Override
        public void onBindViewHolder(RecyclerViewHolderOpportunite viewHolder, int position) {
            viewHolder.updateWithOpportunite(this.listOpportunite.get(position),this.callback);
        }

        @Override
        public int getItemCount() {
            return this.listOpportunite.size();
        }

    public Opportunite getOpportunite(int position){
        return this.listOpportunite.get(position);
    }

}
