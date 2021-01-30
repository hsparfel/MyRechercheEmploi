package com.pouillos.myrechercheemploi.activities;

import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.RequiresApi;

import com.facebook.stetho.Stetho;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.pouillos.myrechercheemploi.R;


import com.pouillos.myrechercheemploi.activities.afficher.AfficherContactActivity;
import com.pouillos.myrechercheemploi.activities.afficher.AfficherSocieteActivity;
import com.pouillos.myrechercheemploi.utils.DateUtils;

import java.util.Date;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import icepick.Icepick;

public class AccueilActivity extends NavDrawerActivity {

    @BindView(R.id.textView)
    TextView textView;

    @BindView(R.id.fabAddSociete)
    FloatingActionButton fabAddSociete;

    @BindView(R.id.fabAddContact)
    FloatingActionButton fabAddContact;

    @BindView(R.id.my_progressBar)
    ProgressBar progressBar;

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

    @OnClick(R.id.fabAddSociete)
    public void setFabAddSocieteClick() {
        ouvrirActiviteSuivante(this, AfficherSocieteActivity.class,false);
    }

    @OnClick(R.id.fabAddContact)
    public void setFabAddContactClick() {
        ouvrirActiviteSuivante(this, AfficherContactActivity.class,false);
    }

    private class AsyncTaskRunnerBD extends AsyncTask<Void, Integer, Void> {

        protected Void doInBackground(Void...voids) {
            publishProgress(0);
            publishProgress(100);
            return null;
        }

        protected void onPostExecute(Void result) {
            progressBar.setVisibility(View.GONE);
            Toast.makeText(AccueilActivity.this, R.string.text_DB_created, Toast.LENGTH_LONG).show();
        }

        @RequiresApi(api = Build.VERSION_CODES.N)
        protected void onProgressUpdate(Integer... integer) {
            progressBar.setProgress(integer[0],true);
        }
    }

}
