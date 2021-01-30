package com.pouillos.myrechercheemploi.recycler.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.recyclerview.widget.RecyclerView;

import com.pouillos.myrechercheemploi.R;
import com.pouillos.myrechercheemploi.entities.Rdv;
import com.pouillos.myrechercheemploi.recycler.holder.RecyclerViewHolderRdv;

import java.util.List;

public class RecyclerAdapterRdv extends RecyclerView.Adapter<RecyclerViewHolderRdv> {

        private List<Rdv> listRdv;

    public interface Listener {
        void onClickRdvButton(int position);
    }

    private final Listener callback;


    public RecyclerAdapterRdv(List<Rdv> listRdv, Listener callback) {
        this.listRdv = listRdv;
        this.callback = callback;
    }

        @Override
        public RecyclerViewHolderRdv onCreateViewHolder(ViewGroup parent, int viewType) {
            Context context = parent.getContext();
            LayoutInflater inflater = LayoutInflater.from(context);
            View view = inflater.inflate(R.layout.recycler_list_rdv, parent, false);

            return new RecyclerViewHolderRdv(view);
        }

        @Override
        public void onBindViewHolder(RecyclerViewHolderRdv viewHolder, int position) {
            viewHolder.updateWithRdv(this.listRdv.get(position),this.callback);
        }

        @Override
        public int getItemCount() {
            return this.listRdv.size();
        }

    public Rdv getRdv(int position){
        return this.listRdv.get(position);
    }

}
