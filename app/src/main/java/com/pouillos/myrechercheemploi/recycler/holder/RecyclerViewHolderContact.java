package com.pouillos.myrechercheemploi.recycler.holder;

import android.view.View;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.pouillos.myrechercheemploi.R;
import com.pouillos.myrechercheemploi.entities.Contact;
import com.pouillos.myrechercheemploi.recycler.adapter.RecyclerAdapterContact;
import com.pouillos.myrechercheemploi.utils.DateUtils;

import java.lang.ref.WeakReference;

import butterknife.BindView;
import butterknife.ButterKnife;

public class RecyclerViewHolderContact extends RecyclerView.ViewHolder implements View.OnClickListener {

    @BindView(R.id.contact)
    TextView contact;


    private WeakReference<RecyclerAdapterContact.Listener> callbackWeakRef;

    public RecyclerViewHolderContact(View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
        //ButterKnife.bind(this);
    }

    public void updateWithContact(Contact contact, RecyclerAdapterContact.Listener callback){
        this.contact.setText(contact.toString());
        this.contact.setOnClickListener(this);
        //4 - Create a new weak Reference to our Listener
        this.callbackWeakRef = new WeakReference<RecyclerAdapterContact.Listener>(callback);
    }


    @Override
    public void onClick(View view) {
        // 5 - When a click happens, we fire our listener.
        RecyclerAdapterContact.Listener callback = callbackWeakRef.get();
        if (callback != null) callback.onClickContactButton(getAdapterPosition());
    }
}
