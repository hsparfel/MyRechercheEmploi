package com.pouillos.myrechercheemploi.recycler.holder;

import android.view.View;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.pouillos.myrechercheemploi.R;
import com.pouillos.myrechercheemploi.entities.Opportunite;
import com.pouillos.myrechercheemploi.recycler.adapter.RecyclerAdapterOpportunite;

import java.lang.ref.WeakReference;

import butterknife.BindView;
import butterknife.ButterKnife;

public class RecyclerViewHolderOpportunite extends RecyclerView.ViewHolder implements View.OnClickListener {

    @BindView(R.id.opportunite)
    TextView opportunite;


    private WeakReference<RecyclerAdapterOpportunite.Listener> callbackWeakRef;

    public RecyclerViewHolderOpportunite(View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
        //ButterKnife.bind(this);
    }

    public void updateWithOpportunite(Opportunite opportunite, RecyclerAdapterOpportunite.Listener callback){
        this.opportunite.setText(opportunite.toString());
        this.opportunite.setOnClickListener(this);
        //4 - Create a new weak Reference to our Listener
        this.callbackWeakRef = new WeakReference<RecyclerAdapterOpportunite.Listener>(callback);
    }


    @Override
    public void onClick(View view) {
        // 5 - When a click happens, we fire our listener.
        RecyclerAdapterOpportunite.Listener callback = callbackWeakRef.get();
        if (callback != null) callback.onClickOpportuniteButton(getAdapterPosition());
    }
}
