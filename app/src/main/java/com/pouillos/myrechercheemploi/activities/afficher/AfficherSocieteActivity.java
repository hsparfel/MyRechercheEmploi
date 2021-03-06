package com.pouillos.myrechercheemploi.activities.afficher;

import android.content.Intent;
import android.os.Bundle;

import com.google.android.material.chip.Chip;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.pouillos.myrechercheemploi.R;
import com.pouillos.myrechercheemploi.activities.AccueilActivity;
import com.pouillos.myrechercheemploi.activities.NavDrawerActivity;
import com.pouillos.myrechercheemploi.entities.Societe;
import com.pouillos.myrechercheemploi.enumeration.TypeSociete;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import icepick.Icepick;

public class AfficherSocieteActivity extends NavDrawerActivity {

    @BindView(R.id.fabSave)
    FloatingActionButton fabSave;
    @BindView(R.id.fabDelete)
    FloatingActionButton fabDelete;

    Societe societe;
    TypeSociete typeSociete;
    boolean hasPremierContact;
    boolean hasEntretienRh;
    boolean hasEntretienTechnique;
    boolean hasEntretienManager;
    boolean hasEntretienAffaire;
    boolean hasTestTechnique;


    @BindView(R.id.textName)
    TextInputEditText textName;
    @BindView(R.id.layoutName)
    TextInputLayout layoutName;
    @BindView(R.id.textAdresse)
    TextInputEditText textAdresse;
    @BindView(R.id.layoutAdresse)
    TextInputLayout layoutAdresse;
    @BindView(R.id.textCp)
    TextInputEditText textCp;
    @BindView(R.id.layoutCp)
    TextInputLayout layoutCp;
    @BindView(R.id.textVille)
    TextInputEditText textVille;
    @BindView(R.id.layoutVille)
    TextInputLayout layoutVille;

    @BindView(R.id.chipEsn)
    Chip chipEsn;
    @BindView(R.id.chipClient)
    Chip chipClient;
    @BindView(R.id.chipPremierContact)
    Chip chipPremierContact;
    @BindView(R.id.chipEntretienRH)
    Chip chipEntretienRH;
    @BindView(R.id.chipEntretienTechnique)
    Chip chipEntretienTechnique;
    @BindView(R.id.chipEntretienManager)
    Chip chipEntretienManager;
    @BindView(R.id.chipEntretienAffaire)
    Chip chipEntretienAffaire;
    @BindView(R.id.chipTestTechnique)
    Chip chipTestTechnique;

    Societe currentSociete;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Icepick.restoreInstanceState(this, savedInstanceState);
        setContentView(R.layout.activity_afficher_societe);

        ButterKnife.bind(this);

        this.configureToolBar();
        this.configureDrawerLayout();
        this.configureNavigationView();

        fabDelete.hide();

        traiterIntent();
    }

    private void traiterIntent() {
        Intent intent = getIntent();
        if (intent.hasExtra("societeId")) {
            fabDelete.show();
            Long societeId = intent.getLongExtra("societeId", 0);
            currentSociete = societeDao.load(societeId);

            textName.setText(currentSociete.getNom());
            if (currentSociete.getAdresse() != null && !currentSociete.getAdresse().equalsIgnoreCase("")) {
                textAdresse.setText(currentSociete.getAdresse());
            }
            if (currentSociete.getCp() != null && !currentSociete.getCp().equalsIgnoreCase("")) {
                textCp.setText(currentSociete.getCp());
            }
            if (currentSociete.getVille() != null && !currentSociete.getVille().equalsIgnoreCase("")) {
                textVille.setText(currentSociete.getVille());
            }
            if (currentSociete.getTypeSociete() == TypeSociete.Esn) {
                chipEsn.setChecked(true);
                typeSociete = TypeSociete.Esn;
            }
            if (currentSociete.getTypeSociete() == TypeSociete.Client) {
                chipClient.setChecked(true);
                typeSociete = TypeSociete.Client;
            }
            chipPremierContact.setChecked(currentSociete.getHasPremierContact());
            hasPremierContact = currentSociete.getHasPremierContact();
            chipEntretienRH.setChecked(currentSociete.getHasEntretienRh());
            hasEntretienRh = currentSociete.getHasEntretienRh();
            chipEntretienTechnique.setChecked(currentSociete.getHasEntretienTechnique());
            hasEntretienTechnique = currentSociete.getHasEntretienTechnique();
            chipEntretienManager.setChecked(currentSociete.getHasEntretienManager());
            hasEntretienManager = currentSociete.getHasEntretienManager();
            chipEntretienAffaire.setChecked(currentSociete.getHasEntretienAffaire());
            hasEntretienAffaire = currentSociete.getHasEntretienAffaire();
            chipTestTechnique.setChecked(currentSociete.getHasTestTechnique());
            hasTestTechnique = currentSociete.getHasTestTechnique();
        }
    }
    
    @OnClick(R.id.chipEsn)
    public void setChipEsnClick() {
        if (chipEsn.isChecked()) {
            chipEsn.setClickable(false);
            chipClient.setClickable(true);
            typeSociete = TypeSociete.Esn;
        }
    }

    @OnClick(R.id.chipClient)
    public void setChipClientClick() {
        if (chipClient.isChecked()) {
            chipEsn.setClickable(true);
            chipClient.setClickable(false);
            typeSociete = TypeSociete.Client;
        }
    }

    @OnClick(R.id.chipPremierContact)
    public void setChipPremierContactClick() {
        if (chipPremierContact.isChecked()) {
            hasPremierContact = true;
        } else {
            hasPremierContact = false;
        }
    }

    @OnClick(R.id.chipEntretienRH)
    public void setChipEntretienRHClick() {
        if (chipEntretienRH.isChecked()) {
            hasEntretienRh = true;
        } else {
            hasEntretienRh = false;
        }
    }

    @OnClick(R.id.chipEntretienTechnique)
    public void setChipEntretienTechniqueClick() {
        if (chipEntretienTechnique.isChecked()) {
            hasEntretienTechnique = true;
        } else {
            hasEntretienTechnique = false;
        }
    }

    @OnClick(R.id.chipEntretienManager)
    public void setChipEntretienManagerClick() {
        if (chipEntretienManager.isChecked()) {
            hasEntretienManager = true;
        } else {
            hasEntretienManager = false;
        }
    }

    @OnClick(R.id.chipEntretienAffaire)
    public void setChipEntretienAffaireClick() {
        if (chipEntretienAffaire.isChecked()) {
            hasEntretienAffaire = true;
        } else {
            hasEntretienAffaire = false;
        }
    }

    @OnClick(R.id.chipTestTechnique)
    public void setChipTestTechniqueClick() {
        if (chipTestTechnique.isChecked()) {
            hasTestTechnique = true;
        } else {
            hasTestTechnique = false;
        }
    }

    @OnClick(R.id.fabSave)
    public void setFabSaveClick() {
        if (isFullRempli()) {
            if (currentSociete == null) {
                societe = new Societe();
            } else {
                societe = currentSociete;
            }
            societe.setTypeSociete(typeSociete);
            String nom = textName.getText().toString();
            String nomCapitale = nom.substring(0,1).toUpperCase()+nom.substring(1);
            societe.setNom(nomCapitale);
            societe.setAdresse(textAdresse.getText().toString());
            societe.setCp(textCp.getText().toString());
            String ville = textVille.getText().toString();
            if (!ville.equalsIgnoreCase("")) {
                String villeCapitale = ville.substring(0, 1).toUpperCase() + ville.substring(1);
                societe.setVille(villeCapitale);
            }
            societe.setHasPremierContact(hasPremierContact);
            societe.setHasEntretienRh(hasEntretienRh);
            societe.setHasEntretienTechnique(hasEntretienTechnique);
            societe.setHasEntretienManager(hasEntretienManager);
            societe.setHasEntretienAffaire(hasEntretienAffaire);
            societe.setHasTestTechnique(hasTestTechnique);
            if (currentSociete == null) {
                societeDao.insert(societe);
            } else {
                societeDao.update(societe);
            }

            ouvrirActiviteSuivante(AfficherSocieteActivity.this, AccueilActivity.class,false);
        }
    }

    @OnClick(R.id.fabDelete)
    public void setFabDeleteClick() {
        if (currentSociete != null) {
            societeDao.delete(currentSociete);
        }
        ouvrirActiviteSuivante(AfficherSocieteActivity.this, AccueilActivity.class,false);
    }

    private boolean isFullRempli() {
        boolean bool = true;

        if (!hasEntretienRh && !hasEntretienTechnique && !hasEntretienManager
                && !hasEntretienAffaire && !hasPremierContact && !hasTestTechnique) {
            bool = false;
            Snackbar.make(chipPremierContact, "Veuillez Selectionner un avancement", Snackbar.LENGTH_SHORT).setAnchorView(layoutCp).show();
        }
        if (!chipEsn.isChecked() && !chipClient.isChecked()) {
            bool = false;
            Snackbar.make(chipEsn, "Veuillez Selectionner un type de société", Snackbar.LENGTH_SHORT).setAnchorView(chipEntretienManager).show();
        }
        if (!isFilled(textName)) {
            bool = false;
            layoutName.setError("Obligatoire");
        }
        if (!textCp.getText().toString().equalsIgnoreCase("") && !isValidZip(textCp)) {
            bool = false;
            Snackbar.make(textCp, "Saisir un CP valide", Snackbar.LENGTH_SHORT).setAnchorView(layoutVille).show();
        }

        if (!textAdresse.getText().toString().equalsIgnoreCase("") || !textCp.getText().toString().equalsIgnoreCase("") || !textVille.getText().toString().equalsIgnoreCase("")) {
            if (textAdresse.getText().toString().equalsIgnoreCase("") || textCp.getText().toString().equalsIgnoreCase("") || textVille.getText().toString().equalsIgnoreCase("")) {
                bool = false;
                Snackbar.make(textCp, "Saisir une adresse complète ou vider", Snackbar.LENGTH_SHORT).setAnchorView(fabSave).show();
            }
        }
        return bool;
    }
}
