package com.pouillos.myrechercheemploi.activities.afficher;

import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.pouillos.myrechercheemploi.R;
import com.pouillos.myrechercheemploi.activities.NavDrawerActivity;
import com.pouillos.myrechercheemploi.entities.Societe;
import com.pouillos.myrechercheemploi.recycler.adapter.RecyclerAdapterSociete;
import com.pouillos.myrechercheemploi.utils.ItemClickSupport;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import icepick.Icepick;

public class AfficherListeSocietesActivity extends NavDrawerActivity implements RecyclerAdapterSociete.Listener {


    private List<Societe> listSocietes;
    private List<Societe> listSocietesBD;

    private RecyclerAdapterSociete adapter;

    @BindView(R.id.listeSocietes)
    RecyclerView listeSocietes;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Icepick.restoreInstanceState(this, savedInstanceState);
        setContentView(R.layout.activity_afficher_liste_societes);
        // 6 - Configure all views
        //  this.configureToolBar();
        // this.configureDrawerLayout();
        //  this.configureNavigationView();
        ButterKnife.bind(this);
        //  activeUser = findActiveUser();

        // traiterIntent();
        listSocietesBD = societeDao.loadAll();

        // 6 - Configure all views
        this.configureToolBar();
        this.configureDrawerLayout();
        this.configureNavigationView();


        configureRecyclerView();
        configureOnClickRecyclerView();
    }

    public void configureRecyclerView() {
        adapter = new RecyclerAdapterSociete(listSocietesBD,this);
        // 3.3 - Attach the adapter to the recyclerview to populate items
        listeSocietes.setAdapter(adapter);
        // 3.4 - Set layout manager to position the items
        //this.recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        listeSocietes.setLayoutManager(new LinearLayoutManager(this));
    }

    private void configureOnClickRecyclerView(){
        ItemClickSupport.addTo(listeSocietes, R.layout.recycler_list_societe)
                .setOnItemClickListener(new ItemClickSupport.OnItemClickListener() {
                    @Override
                    public void onItemClicked(RecyclerView recyclerView, int position, View v) {
                        Log.e("TAG", "Position : "+position);
                    }
                });
    }

    @Override
    public void onClickSocieteButton(int position) {
        Societe societe = adapter.getSociete(position);
        //Toast.makeText(AfficherListeSocieteActivity.this, "a faire click societe", Toast.LENGTH_SHORT).show();
        //societe.delete();
        ouvrirActiviteSuivante(AfficherListeSocietesActivity.this,AfficherSocieteActivity.class,"societeId",societe.getId(),true);

        //listSocietesBD.remove(position);
        //adapter.notifyItemRemoved(position);
    }




}
