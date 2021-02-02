package com.pouillos.myrechercheemploi.activities.afficher;

import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.pouillos.myrechercheemploi.R;
import com.pouillos.myrechercheemploi.activities.NavDrawerActivity;
import com.pouillos.myrechercheemploi.entities.Opportunite;
import com.pouillos.myrechercheemploi.recycler.adapter.RecyclerAdapterOpportunite;
import com.pouillos.myrechercheemploi.utils.ItemClickSupport;

import java.util.Collections;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import icepick.Icepick;

public class AfficherListeOpportunitesActivity extends NavDrawerActivity implements RecyclerAdapterOpportunite.Listener {


    private List<Opportunite> listOpportunites;
    private List<Opportunite> listOpportunitesBD;

    private RecyclerAdapterOpportunite adapter;

    @BindView(R.id.listeOpportunites)
    RecyclerView listeOpportunites;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Icepick.restoreInstanceState(this, savedInstanceState);
        setContentView(R.layout.activity_afficher_liste_opportunites);
        // 6 - Configure all views
        //  this.configureToolBar();
        // this.configureDrawerLayout();
        //  this.configureNavigationView();
        ButterKnife.bind(this);
        //  activeUser = findActiveUser();

        // traiterIntent();
        listOpportunitesBD = opportuniteDao.loadAll();
        Collections.sort(listOpportunitesBD);
        // 6 - Configure all views
        this.configureToolBar();
        this.configureDrawerLayout();
        this.configureNavigationView();


        configureRecyclerView();
        configureOnClickRecyclerView();
    }

    public void configureRecyclerView() {
        adapter = new RecyclerAdapterOpportunite(listOpportunitesBD,this);
        // 3.3 - Attach the adapter to the recyclerview to populate items
        listeOpportunites.setAdapter(adapter);
        // 3.4 - Set layout manager to position the items
        //this.recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        listeOpportunites.setLayoutManager(new LinearLayoutManager(this));
    }

    private void configureOnClickRecyclerView(){
        ItemClickSupport.addTo(listeOpportunites, R.layout.recycler_list_opportunite)
                .setOnItemClickListener(new ItemClickSupport.OnItemClickListener() {
                    @Override
                    public void onItemClicked(RecyclerView recyclerView, int position, View v) {
                        Log.e("TAG", "Position : "+position);
                    }
                });
    }

    @Override
    public void onClickOpportuniteButton(int position) {
        Opportunite opportunite = adapter.getOpportunite(position);
        //Toast.makeText(AfficherListeOpportuniteActivity.this, "a faire click opportunite", Toast.LENGTH_SHORT).show();
        //opportunite.delete();
        ouvrirActiviteSuivante(AfficherListeOpportunitesActivity.this,AfficherOpportuniteActivity.class,"opportuniteId",opportunite.getId(),true);

        //listOpportunitesBD.remove(position);
        //adapter.notifyItemRemoved(position);
    }




}
