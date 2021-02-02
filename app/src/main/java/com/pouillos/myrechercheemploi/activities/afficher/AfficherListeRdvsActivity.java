package com.pouillos.myrechercheemploi.activities.afficher;

import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.pouillos.myrechercheemploi.R;
import com.pouillos.myrechercheemploi.activities.NavDrawerActivity;
import com.pouillos.myrechercheemploi.entities.Rdv;
import com.pouillos.myrechercheemploi.recycler.adapter.RecyclerAdapterRdv;
import com.pouillos.myrechercheemploi.utils.ItemClickSupport;

import java.util.Collections;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import icepick.Icepick;

public class AfficherListeRdvsActivity extends NavDrawerActivity implements RecyclerAdapterRdv.Listener {


    private List<Rdv> listRdvs;
    private List<Rdv> listRdvsBD;

    private RecyclerAdapterRdv adapter;

    @BindView(R.id.listeRdvs)
    RecyclerView listeRdvs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Icepick.restoreInstanceState(this, savedInstanceState);
        setContentView(R.layout.activity_afficher_liste_rdvs);
        // 6 - Configure all views
        //  this.configureToolBar();
        // this.configureDrawerLayout();
        //  this.configureNavigationView();
        ButterKnife.bind(this);
        //  activeUser = findActiveUser();

        // traiterIntent();
        listRdvsBD = rdvDao.loadAll();
        Collections.sort(listRdvsBD);
        // 6 - Configure all views
        this.configureToolBar();
        this.configureDrawerLayout();
        this.configureNavigationView();


        configureRecyclerView();
        configureOnClickRecyclerView();
    }

    public void configureRecyclerView() {
        adapter = new RecyclerAdapterRdv(listRdvsBD,this);
        // 3.3 - Attach the adapter to the recyclerview to populate items
        listeRdvs.setAdapter(adapter);
        // 3.4 - Set layout manager to position the items
        //this.recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        listeRdvs.setLayoutManager(new LinearLayoutManager(this));
    }

    private void configureOnClickRecyclerView(){
        ItemClickSupport.addTo(listeRdvs, R.layout.recycler_list_rdv)
                .setOnItemClickListener(new ItemClickSupport.OnItemClickListener() {
                    @Override
                    public void onItemClicked(RecyclerView recyclerView, int position, View v) {
                        Log.e("TAG", "Position : "+position);
                    }
                });
    }

    @Override
    public void onClickRdvButton(int position) {
        Rdv rdv = adapter.getRdv(position);
        //Toast.makeText(AfficherListeRdvActivity.this, "a faire click rdv", Toast.LENGTH_SHORT).show();
        //rdv.delete();
        ouvrirActiviteSuivante(AfficherListeRdvsActivity.this,AfficherRdvActivity.class,"rdvId",rdv.getId(),true);

        //listRdvsBD.remove(position);
        //adapter.notifyItemRemoved(position);
    }




}
