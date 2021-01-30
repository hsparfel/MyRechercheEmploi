package com.pouillos.myrechercheemploi.activities.afficher;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AutoCompleteTextView;
import android.widget.ProgressBar;

import androidx.annotation.RequiresApi;
import androidx.appcompat.widget.Toolbar;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.pouillos.myrechercheemploi.R;
import com.pouillos.myrechercheemploi.activities.AccueilActivity;
import com.pouillos.myrechercheemploi.activities.NavDrawerActivity;
import com.pouillos.myrechercheemploi.entities.Opportunite;
import com.pouillos.myrechercheemploi.entities.Contact;
import com.pouillos.myrechercheemploi.enumeration.AvancementOpportunite;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import icepick.Icepick;
import icepick.State;

public class AfficherOpportuniteActivity extends NavDrawerActivity implements Serializable, AdapterView.OnItemClickListener{

    @BindView(R.id.fabSave)
    FloatingActionButton fabSave;
    @BindView(R.id.fabDelete)
    FloatingActionButton fabDelete;

    Opportunite opportunite;
    AvancementOpportunite typeOpportunite;


    Contact contactSelected;
    List<Contact> listContactsBD;
    
    @BindView(R.id.selectContact)
    AutoCompleteTextView selectedContact;
    @BindView(R.id.listContacts)
    TextInputLayout listContacts;

    @State
    AvancementOpportunite typeOpportuniteSelected;
    List<AvancementOpportunite> listAvancementOpportunitesBD;
    
    @BindView(R.id.selectAvancementOpportunite)
    AutoCompleteTextView selectedAvancementOpportunite;
    @BindView(R.id.listAvancementOpportunites)
    TextInputLayout listAvancementOpportunites;


    @BindView(R.id.textDetail)
    TextInputEditText textDetail;
    @BindView(R.id.layoutDetail)
    TextInputLayout layoutDetail;

    Opportunite currentOpportunite;

    @BindView(R.id.my_progressBar)
    ProgressBar progressBar;
    @BindView(R.id.activity_main_toolbar)
    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Icepick.restoreInstanceState(this, savedInstanceState);
        setContentView(R.layout.activity_afficher_opportunite);

        ButterKnife.bind(this);

        this.configureToolBar();
        this.configureDrawerLayout();
        this.configureNavigationView();

        progressBar.setVisibility(View.VISIBLE);

        AfficherOpportuniteActivity.AsyncTaskRunner runner = new AfficherOpportuniteActivity.AsyncTaskRunner();
        runner.execute();

        fabDelete.hide();

        traiterIntent();

        selectedContact.setOnItemClickListener(this);
    }

    private void traiterIntent() {
        Intent intent = getIntent();
        if (intent.hasExtra("opportuniteId")) {
            fabDelete.show();
            Long opportuniteId = intent.getLongExtra("opportuniteId", 0);
            currentOpportunite = opportuniteDao.load(opportuniteId);
            textDetail.setText(currentOpportunite.getDetail());
            contactSelected = currentOpportunite.getContact();
            int position = 0;
            for (Contact contact : listContactsBD) {
                if (contact != contactSelected) {
                    position ++;
                } else {
                    break;
                }
            }
            selectedContact.setText(contactSelected.toString(),false);
            typeOpportuniteSelected = currentOpportunite.getAvancementOpportunite();
            selectedAvancementOpportunite.setText(typeOpportuniteSelected.toString(),false);
        }
    }


    @OnClick(R.id.fabSave)
    public void setFabSaveClick() {
        if (isFullRempli()) {
            if (currentOpportunite == null) {
                opportunite = new Opportunite();
            } else {
                opportunite = currentOpportunite;
            }
            opportunite.setAvancementOpportunite(typeOpportunite);
            opportunite.setDetail(textDetail.getText().toString());
            opportunite.setContact(contactSelected);
            opportunite.setAvancementOpportunite(AvancementOpportunite.rechercheParNom(selectedAvancementOpportunite.getText().toString()));

            if (currentOpportunite == null) {
                opportuniteDao.insert(opportunite);
            } else {
                opportuniteDao.update(opportunite);
            }
            ouvrirActiviteSuivante(AfficherOpportuniteActivity.this, AccueilActivity.class,false);
        }
    }

    @OnClick(R.id.fabDelete)
    public void setFabDeleteClick() {
        if (currentOpportunite != null) {
            opportuniteDao.delete(currentOpportunite);
        }
        ouvrirActiviteSuivante(AfficherOpportuniteActivity.this, AccueilActivity.class,false);
    }

    private boolean isFullRempli() {
        boolean bool = true;

        if (!isFilled(selectedContact)) {
            bool = false;
            Snackbar.make(selectedContact, "Veuillez Selectionner une contact", Snackbar.LENGTH_SHORT).setAnchorView(selectedAvancementOpportunite).show();
        }
        if (!isFilled(selectedAvancementOpportunite)) {
            bool = false;
            Snackbar.make(selectedAvancementOpportunite, "Veuillez Selectionner un avancement d'opportunite", Snackbar.LENGTH_SHORT).setAnchorView(fabSave).show();
        }

        if (!isFilled(textDetail)) {
            bool = false;
            layoutDetail.setError("Obligatoire");
        }
        return bool;
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            contactSelected = listContactsBD.get(position);
    }

    public class AsyncTaskRunner extends AsyncTask<Void, Integer, Void> {

        protected Void doInBackground(Void...voids) {
            publishProgress(0);
            listContactsBD = contactDao.loadAll();
            Collections.sort(listContactsBD);
            listAvancementOpportunitesBD = new ArrayList<AvancementOpportunite>();
            listAvancementOpportunitesBD.addAll(new ArrayList<AvancementOpportunite>(Arrays.asList(AvancementOpportunite.values())));
            listAvancementOpportunitesBD.remove(0);
            publishProgress(100);
            return null;
        }

        @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
        protected void onPostExecute(Void result) {
            progressBar.setVisibility(View.GONE);
            if (listAvancementOpportunitesBD.size() !=0) {
                buildDropdownMenu(listAvancementOpportunitesBD, AfficherOpportuniteActivity.this,selectedAvancementOpportunite);
            }
            if (listContactsBD.size() == 0) {
                Snackbar.make(selectedContact, "Pas de contacts en BD", Snackbar.LENGTH_SHORT).setAnchorView(selectedContact).show();
            } else {
                buildDropdownMenu(listContactsBD, AfficherOpportuniteActivity.this,selectedContact);

                traiterIntent();
            }
        }

        @RequiresApi(api = Build.VERSION_CODES.N)
        protected void onProgressUpdate(Integer... integer) {
            progressBar.setProgress(integer[0],true);
        }
    }
}
