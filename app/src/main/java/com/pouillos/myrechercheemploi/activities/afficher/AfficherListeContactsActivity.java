package com.pouillos.myrechercheemploi.activities.afficher;

import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.pouillos.myrechercheemploi.R;
import com.pouillos.myrechercheemploi.activities.NavDrawerActivity;
import com.pouillos.myrechercheemploi.entities.Contact;
import com.pouillos.myrechercheemploi.recycler.adapter.RecyclerAdapterContact;
import com.pouillos.myrechercheemploi.utils.ItemClickSupport;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import icepick.Icepick;

public class AfficherListeContactsActivity extends NavDrawerActivity implements RecyclerAdapterContact.Listener {


    private List<Contact> listContacts;
    private List<Contact> listContactsBD;

    private RecyclerAdapterContact adapter;

    @BindView(R.id.listeContacts)
    RecyclerView listeContacts;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Icepick.restoreInstanceState(this, savedInstanceState);
        setContentView(R.layout.activity_afficher_liste_contacts);
        // 6 - Configure all views
        //  this.configureToolBar();
        // this.configureDrawerLayout();
        //  this.configureNavigationView();
        ButterKnife.bind(this);
        //  activeUser = findActiveUser();

        // traiterIntent();
        listContactsBD = contactDao.loadAll();

        // 6 - Configure all views
        this.configureToolBar();
        this.configureDrawerLayout();
        this.configureNavigationView();


        configureRecyclerView();
        configureOnClickRecyclerView();
    }

    public void configureRecyclerView() {
        adapter = new RecyclerAdapterContact(listContactsBD,this);
        // 3.3 - Attach the adapter to the recyclerview to populate items
        listeContacts.setAdapter(adapter);
        // 3.4 - Set layout manager to position the items
        //this.recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        listeContacts.setLayoutManager(new LinearLayoutManager(this));
    }

    private void configureOnClickRecyclerView(){
        ItemClickSupport.addTo(listeContacts, R.layout.recycler_list_contact)
                .setOnItemClickListener(new ItemClickSupport.OnItemClickListener() {
                    @Override
                    public void onItemClicked(RecyclerView recyclerView, int position, View v) {
                        Log.e("TAG", "Position : "+position);
                    }
                });
    }

    @Override
    public void onClickContactButton(int position) {
        Contact contact = adapter.getContact(position);
        //Toast.makeText(AfficherListeContactActivity.this, "a faire click contact", Toast.LENGTH_SHORT).show();
        //contact.delete();
        ouvrirActiviteSuivante(AfficherListeContactsActivity.this,AfficherContactActivity.class,"contactId",contact.getId(),true);

        //listContactsBD.remove(position);
        //adapter.notifyItemRemoved(position);
    }




}
