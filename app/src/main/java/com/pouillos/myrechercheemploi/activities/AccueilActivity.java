package com.pouillos.myrechercheemploi.activities;

import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.facebook.stetho.Stetho;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.pouillos.myrechercheemploi.R;


import com.pouillos.myrechercheemploi.activities.afficher.AfficherContactActivity;
import com.pouillos.myrechercheemploi.activities.afficher.AfficherListeRdvsActivity;
import com.pouillos.myrechercheemploi.activities.afficher.AfficherRdvActivity;
import com.pouillos.myrechercheemploi.activities.afficher.AfficherSocieteActivity;
import com.pouillos.myrechercheemploi.entities.Rdv;
import com.pouillos.myrechercheemploi.recycler.adapter.RecyclerAdapterRdv;
import com.pouillos.myrechercheemploi.utils.DateUtils;
import com.pouillos.myrechercheemploi.utils.ItemClickSupport;

import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import icepick.Icepick;

public class AccueilActivity extends NavDrawerActivity implements RecyclerAdapterRdv.Listener {

    @BindView(R.id.textView)
    TextView textView;

    @BindView(R.id.my_progressBar)
    ProgressBar progressBar;

    @BindView(R.id.listeRdvFuturs)
    RecyclerView listeRdvFuturs;

    private List<Rdv> myListRdv;
    private RecyclerAdapterRdv adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Icepick.restoreInstanceState(this, savedInstanceState);
        setContentView(R.layout.activity_accueil);
        Stetho.initializeWithDefaults(this);

        this.configureToolBar();
        this.configureDrawerLayout();
        this.configureNavigationView();

        ButterKnife.bind(this);

        textView.setText(DateUtils.ecrireDateLettre(new Date()));

        progressBar.setVisibility(View.VISIBLE);
        AccueilActivity.AsyncTaskRunnerBD runnerBD = new AccueilActivity.AsyncTaskRunnerBD();
        runnerBD.execute();

    }



    private class AsyncTaskRunnerBD extends AsyncTask<Void, Integer, Void> {

        protected Void doInBackground(Void...voids) {
            publishProgress(0);
            myListRdv = rdvDao.loadAll();
            publishProgress(100);
            return null;
        }

        protected void onPostExecute(Void result) {
            progressBar.setVisibility(View.GONE);
            Toast.makeText(AccueilActivity.this, R.string.text_DB_created, Toast.LENGTH_LONG).show();
            myListRdv = Rdv.listerRdvFuturs(myListRdv, new Date());

            configureRecyclerView();
            configureOnClickRecyclerView();
        }

        @RequiresApi(api = Build.VERSION_CODES.N)
        protected void onProgressUpdate(Integer... integer) {
            progressBar.setProgress(integer[0],true);
        }
    }

    public void configureRecyclerView() {
        adapter = new RecyclerAdapterRdv(myListRdv,this);
        // 3.3 - Attach the adapter to the recyclerview to populate items
        listeRdvFuturs.setAdapter(adapter);
        // 3.4 - Set layout manager to position the items
        //this.recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        listeRdvFuturs.setLayoutManager(new LinearLayoutManager(this));
    }

    private void configureOnClickRecyclerView(){
        ItemClickSupport.addTo(listeRdvFuturs, R.layout.recycler_list_rdv)
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
        ouvrirActiviteSuivante(AccueilActivity.this, AfficherRdvActivity.class,"rdvId",rdv.getId(),true);

        //listRdvsBD.remove(position);
        //adapter.notifyItemRemoved(position);
    }

}
