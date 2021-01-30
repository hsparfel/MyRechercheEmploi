package com.pouillos.myrechercheemploi.recycler.holder;

import android.view.View;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.pouillos.myrechercheemploi.R;
import com.pouillos.myrechercheemploi.entities.Rdv;
import com.pouillos.myrechercheemploi.recycler.adapter.RecyclerAdapterRdv;

import java.lang.ref.WeakReference;

import butterknife.BindView;
import butterknife.ButterKnife;

public class RecyclerViewHolderRdv extends RecyclerView.ViewHolder implements View.OnClickListener {

    @BindView(R.id.rdv)
    TextView rdv;


    private WeakReference<RecyclerAdapterRdv.Listener> callbackWeakRef;

    public RecyclerViewHolderRdv(View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
        //ButterKnife.bind(this);
    }

    public void updateWithRdv(Rdv rdv, RecyclerAdapterRdv.Listener callback){
        this.rdv.setText(rdv.toString());
        this.rdv.setOnClickListener(this);
        //4 - Create a new weak Reference to our Listener
        this.callbackWeakRef = new WeakReference<RecyclerAdapterRdv.Listener>(callback);
    }


    @Override
    public void onClick(View view) {
        // 5 - When a click happens, we fire our listener.
        RecyclerAdapterRdv.Listener callback = callbackWeakRef.get();
        if (callback != null) callback.onClickRdvButton(getAdapterPosition());
    }
}
