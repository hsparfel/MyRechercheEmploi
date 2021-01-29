package com.pouillos.myrechercheemploi.recycler.holder;

import android.view.View;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.pouillos.myrechercheemploi.R;
import com.pouillos.myrechercheemploi.entities.Contact;
import com.pouillos.myrechercheemploi.entities.Societe;
import com.pouillos.myrechercheemploi.recycler.adapter.RecyclerAdapterContact;
import com.pouillos.myrechercheemploi.recycler.adapter.RecyclerAdapterSociete;
import com.pouillos.myrechercheemploi.utils.DateUtils;

import java.lang.ref.WeakReference;

import butterknife.BindView;
import butterknife.ButterKnife;

public class RecyclerViewHolderSociete extends RecyclerView.ViewHolder implements View.OnClickListener {

    @BindView(R.id.societe)
    TextView societe;


    private WeakReference<RecyclerAdapterSociete.Listener> callbackWeakRef;

    public RecyclerViewHolderSociete(View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
        //ButterKnife.bind(this);
    }

    public void updateWithSociete(Societe societe, RecyclerAdapterSociete.Listener callback){

        this.societe.setText(societe.toString());
        this.societe.setOnClickListener(this);
        //4 - Create a new weak Reference to our Listener
        this.callbackWeakRef = new WeakReference<RecyclerAdapterSociete.Listener>(callback);
    }


    @Override
    public void onClick(View view) {
        // 5 - When a click happens, we fire our listener.
        RecyclerAdapterSociete.Listener callback = callbackWeakRef.get();
        if (callback != null) callback.onClickSocieteButton(getAdapterPosition());
    }
}
