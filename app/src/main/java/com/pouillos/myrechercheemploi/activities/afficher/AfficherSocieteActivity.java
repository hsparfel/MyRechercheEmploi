package com.pouillos.myrechercheemploi.activities.afficher;

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

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import icepick.Icepick;

public class AfficherSocieteActivity extends NavDrawerActivity {

    @BindView(R.id.fabSave)
    FloatingActionButton fabSave;
    @BindView(R.id.fabModify)
    FloatingActionButton fabModify;
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



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Icepick.restoreInstanceState(this, savedInstanceState);
        setContentView(R.layout.activity_afficher_societe);

        ButterKnife.bind(this);

        this.configureToolBar();
        this.configureDrawerLayout();
        this.configureNavigationView();
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
            societe = new Societe();
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
            societeDao.insert(societe);
            ouvrirActiviteSuivante(AfficherSocieteActivity.this, AccueilActivity.class,false);
        }
    }

    @OnClick(R.id.fabModify)
    public void setFabModifyClick() {
        //todo
    }

    @OnClick(R.id.fabDelete)
    public void setFabDeleteClick() {
        //todo
    }

    private boolean isFullRempli() {
        boolean bool = true;

        if (!hasEntretienRh && !hasEntretienTechnique && !hasEntretienManager
                && !hasEntretienAffaire && !hasPremierContact && !hasTestTechnique) {
            bool = false;
            Snackbar.make(chipPremierContact, "Veuillez Selectionner un avancement", Snackbar.LENGTH_SHORT).setAnchorView(chipPremierContact).show();
        }
        if (!chipEsn.isChecked() && !chipClient.isChecked()) {
            bool = false;
            Snackbar.make(chipEsn, "Veuillez Selectionner un type de société", Snackbar.LENGTH_SHORT).setAnchorView(chipEsn).show();
        }
        if (!isFilled(textName)) {
            bool = false;
            layoutName.setError("Obligatoire");
        }
        return bool;
    }
}
