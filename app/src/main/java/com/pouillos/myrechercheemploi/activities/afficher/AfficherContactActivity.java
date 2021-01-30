package com.pouillos.myrechercheemploi.activities.afficher;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.appcompat.widget.Toolbar;

import com.google.android.material.chip.Chip;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.pouillos.myrechercheemploi.R;
import com.pouillos.myrechercheemploi.activities.AccueilActivity;
import com.pouillos.myrechercheemploi.activities.NavDrawerActivity;
import com.pouillos.myrechercheemploi.entities.Contact;
import com.pouillos.myrechercheemploi.entities.Societe;
import com.pouillos.myrechercheemploi.enumeration.TypeContact;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import icepick.Icepick;
import icepick.State;

public class AfficherContactActivity extends NavDrawerActivity implements Serializable, AdapterView.OnItemClickListener{

    @BindView(R.id.fabSave)
    FloatingActionButton fabSave;
    @BindView(R.id.fabDelete)
    FloatingActionButton fabDelete;

    Contact contact;
    TypeContact typeContact;


    Societe societeSelected;
    List<Societe> listSocietesBD;
    ArrayAdapter adapterSociete;
    @BindView(R.id.selectSociete)
    AutoCompleteTextView selectedSociete;
    @BindView(R.id.listSocietes)
    TextInputLayout listSocietes;

    @State
    TypeContact typeContactSelected;
    List<TypeContact> listTypeContactsBD;
    ArrayAdapter adapterTypeContact;
    @BindView(R.id.selectTypeContact)
    AutoCompleteTextView selectedTypeContact;
    @BindView(R.id.listTypeContacts)
    TextInputLayout listTypeContacts;


    @BindView(R.id.textName)
    TextInputEditText textName;
    @BindView(R.id.layoutName)
    TextInputLayout layoutName;
    @BindView(R.id.textPrenom)
    TextInputEditText textPrenom;
    @BindView(R.id.layoutPrenom)
    TextInputLayout layoutPrenom;
    @BindView(R.id.textTel)
    TextInputEditText textTel;
    @BindView(R.id.layoutTel)
    TextInputLayout layoutTel;
    @BindView(R.id.textEmail)
    TextInputEditText textEmail;
    @BindView(R.id.layoutEmail)
    TextInputLayout layoutEmail;

    Contact currentContact;

    @BindView(R.id.my_progressBar)
    ProgressBar progressBar;
    @BindView(R.id.activity_main_toolbar)
    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Icepick.restoreInstanceState(this, savedInstanceState);
        setContentView(R.layout.activity_afficher_contact);

        ButterKnife.bind(this);

        this.configureToolBar();
        this.configureDrawerLayout();
        this.configureNavigationView();

        progressBar.setVisibility(View.VISIBLE);

        AfficherContactActivity.AsyncTaskRunner runner = new AfficherContactActivity.AsyncTaskRunner();
        runner.execute();

        fabDelete.hide();

        traiterIntent();

        selectedSociete.setOnItemClickListener(this);
    }

    private void traiterIntent() {
        Intent intent = getIntent();
        if (intent.hasExtra("contactId")) {
            fabDelete.show();
            Long contactId = intent.getLongExtra("contactId", 0);
            currentContact = contactDao.load(contactId);
            textName.setText(currentContact.getNom());
            textPrenom.setText(currentContact.getPrenom());
            if (currentContact.getTel() != null && !currentContact.getTel().equalsIgnoreCase("")) {
                textTel.setText(currentContact.getTel());
            }
            if (currentContact.getMail() != null && !currentContact.getMail().equalsIgnoreCase("")) {
                textEmail.setText(currentContact.getMail());
            }
            societeSelected = currentContact.getSociete();
            int position = 0;
            for (Societe societe : listSocietesBD) {
                if (societe != societeSelected) {
                    position ++;
                } else {
                    break;
                }
            }
            selectedSociete.setText(selectedSociete.getAdapter().getItem(position).toString(), false);
        }
    }


    @OnClick(R.id.fabSave)
    public void setFabSaveClick() {
        if (isFullRempli()) {
            if (currentContact == null) {
                contact = new Contact();
            } else {
                contact = currentContact;
            }
            contact.setTypeContact(typeContact);
            String nom = textName.getText().toString();
            String nomCapitale = nom.substring(0,1).toUpperCase()+nom.substring(1);
            contact.setNom(nomCapitale);
            String prenom = textPrenom.getText().toString();
            String prenomCapitale = prenom.substring(0,1).toUpperCase()+prenom.substring(1);
            contact.setPrenom(prenomCapitale);
            contact.setTel(textTel.getText().toString());
            contact.setMail(textEmail.getText().toString());
            if (currentContact == null) {
                contactDao.insert(contact);
            } else {
                contactDao.update(contact);
            }
            ouvrirActiviteSuivante(AfficherContactActivity.this, AccueilActivity.class,false);
        }
    }

    @OnClick(R.id.fabDelete)
    public void setFabDeleteClick() {
        if (currentContact != null) {
            contactDao.delete(currentContact);
        }
        ouvrirActiviteSuivante(AfficherContactActivity.this, AccueilActivity.class,false);
    }

    private boolean isFullRempli() {
        boolean bool = true;

        if (!isFilled(selectedSociete)) {
            bool = false;
            Snackbar.make(selectedSociete, "Veuillez Selectionner une societe", Snackbar.LENGTH_SHORT).setAnchorView(selectedTypeContact).show();
        }
        if (!isFilled(selectedTypeContact)) {
            bool = false;
            Snackbar.make(selectedTypeContact, "Veuillez Selectionner un type de contact", Snackbar.LENGTH_SHORT).setAnchorView(layoutTel).show();
        }
        if (!isFilled(textPrenom)) {
            bool = false;
            layoutPrenom.setError("Obligatoire");
        }
        if (!isFilled(textName)) {
            bool = false;
            layoutName.setError("Obligatoire");
        }

        if (!textTel.getText().toString().equalsIgnoreCase("") && !isValidTel(textTel)) {
            bool = false;
            Snackbar.make(textTel, "Saisir un Tel valide", Snackbar.LENGTH_SHORT).setAnchorView(layoutEmail).show();
        }
        if (!textEmail.getText().toString().equalsIgnoreCase("") && !isValidEmail(textEmail)) {
            bool = false;
            Snackbar.make(textEmail, "Saisir un Email valide", Snackbar.LENGTH_SHORT).setAnchorView(fabSave).show();
        }
        return bool;
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        societeSelected = listSocietesBD.get(position);
    }

    public class AsyncTaskRunner extends AsyncTask<Void, Integer, Void> {

        protected Void doInBackground(Void...voids) {
            publishProgress(0);
            listSocietesBD = societeDao.loadAll();
            Collections.sort(listSocietesBD);
            publishProgress(100);
            return null;
        }

        @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
        protected void onPostExecute(Void result) {
            progressBar.setVisibility(View.GONE);
            if (listSocietesBD.size() == 0) {
                Snackbar.make(selectedSociete, "Pas de societes en BD", Snackbar.LENGTH_SHORT).setAnchorView(selectedSociete).show();
            } else {
                buildDropdownMenu(listSocietesBD, AfficherContactActivity.this,selectedSociete);

                traiterIntent();
            }
        }

        @RequiresApi(api = Build.VERSION_CODES.N)
        protected void onProgressUpdate(Integer... integer) {
            progressBar.setProgress(integer[0],true);
        }
    }
}
