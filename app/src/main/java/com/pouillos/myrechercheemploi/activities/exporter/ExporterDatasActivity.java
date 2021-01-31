package com.pouillos.myrechercheemploi.activities.exporter;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.core.content.FileProvider;

import com.facebook.stetho.Stetho;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.pouillos.myrechercheemploi.R;
import com.pouillos.myrechercheemploi.activities.AccueilActivity;
import com.pouillos.myrechercheemploi.activities.NavDrawerActivity;
import com.pouillos.myrechercheemploi.activities.afficher.AfficherContactActivity;
import com.pouillos.myrechercheemploi.activities.afficher.AfficherSocieteActivity;
import com.pouillos.myrechercheemploi.entities.Contact;
import com.pouillos.myrechercheemploi.entities.Opportunite;
import com.pouillos.myrechercheemploi.entities.Rdv;
import com.pouillos.myrechercheemploi.entities.Societe;
import com.pouillos.myrechercheemploi.enumeration.AvancementSociete;
import com.pouillos.myrechercheemploi.utils.DateUtils;

import java.io.File;
import java.io.FileOutputStream;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import icepick.Icepick;

public class ExporterDatasActivity extends NavDrawerActivity {



    @BindView(R.id.my_progressBar)
    ProgressBar progressBar;

    List<Societe> listSocietes;
    List<Contact> listContacts;
    List<Opportunite> listOpportunites;
    List<Rdv> listRdvs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Icepick.restoreInstanceState(this, savedInstanceState);
        setContentView(R.layout.activity_exporter_datas);
        Stetho.initializeWithDefaults(this);

        this.configureToolBar();
        this.configureDrawerLayout();
        this.configureNavigationView();

        ButterKnife.bind(this);

        //textView.setText(DateUtils.ecrireDateLettre(new Date()));

        progressBar.setVisibility(View.VISIBLE);

        ExporterDatasActivity.AsyncTaskRunnerBD runnerBD = new ExporterDatasActivity.AsyncTaskRunnerBD();
        runnerBD.execute();

    }

    private class AsyncTaskRunnerBD extends AsyncTask<Void, Integer, Void> {

        protected Void doInBackground(Void...voids) {
            publishProgress(0);
            listSocietes = societeDao.loadAll();
            Collections.sort(listSocietes);
            publishProgress(20);
            listContacts = contactDao.loadAll();
            Collections.sort(listContacts);
            publishProgress(40);
            listOpportunites = opportuniteDao.loadAll();
            Collections.sort(listOpportunites);
            publishProgress(60);
            listRdvs = rdvDao.loadAll();
            Collections.sort(listRdvs);
            publishProgress(80);
            publishProgress(100);
            return null;
        }

        protected void onPostExecute(Void result) {
            progressBar.setVisibility(View.GONE);
            //Toast.makeText(AccueilActivity.this, R.string.text_DB_created, Toast.LENGTH_LONG).show();
        }

        @RequiresApi(api = Build.VERSION_CODES.N)
        protected void onProgressUpdate(Integer... integer) {
            progressBar.setProgress(integer[0],true);
        }
    }

    public void exportTout(View view) {
        exportSocietes(view);
        exportContacts(view);
        exportOpportunites(view);
        exportRdvs(view);
    }


    public void exportSocietes(View view) {
        StringBuilder data = new StringBuilder();
        //creation titres colonnes
        data.append("Nom,Type Societes,Avancement,Adresse,CP,Ville");
        //remplir data avec données DB
        for (Societe societe:listSocietes){
            String string = "\n";
            string += societe.getNom()+",";
            string += societe.getTypeSociete()+",";
            if (societe.getHasPremierContact()) {
                string += AvancementSociete.PremierContact.name +" / ";
            }
            if (societe.getHasEntretienRh()) {
                string += AvancementSociete.EntretienRH.name+" / ";
            }
            if (societe.getHasEntretienTechnique()) {
                string += AvancementSociete.EntretienTechnique.name +" / ";
            }
            if (societe.getHasEntretienManager()) {
                string += AvancementSociete.EntretienManager.name +" / ";
            }
            if (societe.getHasEntretienAffaire()) {
                string += AvancementSociete.EntretienAffaire.name +" / ";
            }
            if (societe.getHasTestTechnique()) {
                string += AvancementSociete.TestTechnique.name;
            }
            string += societe.getAdresse()+",";
            string += societe.getCp()+",";
            string += societe.getVille();
            data.append(string);
        }
        try {
            //sauvegarde du fichier
            FileOutputStream out = openFileOutput("export_societes.csv",MODE_PRIVATE);
            out.write(data.toString().getBytes());
            out.close();
            //export
            Context context = getApplicationContext();
            File fileLocation = new File(getFilesDir(),"export_societes.csv");
            Uri path = FileProvider.getUriForFile(context,"com.pouillos.myrechercheemploi.fileprovider",fileLocation);
            Intent fileIntent = new Intent(Intent.ACTION_SEND);
            fileIntent.setType("ext/csv");
            fileIntent.putExtra(Intent.EXTRA_SUBJECT,"Search Jop App - Export Societes");
            fileIntent.setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
            fileIntent.putExtra(Intent.EXTRA_STREAM,path);
            startActivity(Intent.createChooser(fileIntent,"send mail"));
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    public void exportContacts(View view) {
        StringBuilder data = new StringBuilder();
        //creation titres colonnes
        data.append("Nom,Prenom,Type Contact,Societe,Tel,Mail");
        //remplir data avec données DB
        for (Contact contact:listContacts){
            String string = "\n";
            string += contact.getNom()+",";
            string += contact.getPrenom()+",";
            string += contact.getTypeContact()+",";
            string += contact.getSociete().getNom()+",";
            string += contact.getTel()+",";
            string += contact.getMail();
            data.append(string);
        }
        try {
            //sauvegarde du fichier
            FileOutputStream out = openFileOutput("export_contacts.csv",MODE_PRIVATE);
            out.write(data.toString().getBytes());
            out.close();
            //export
            Context context = getApplicationContext();
            File fileLocation = new File(getFilesDir(),"export_contacts.csv");
            Uri path = FileProvider.getUriForFile(context,"com.pouillos.myrechercheemploi.fileprovider",fileLocation);
            Intent fileIntent = new Intent(Intent.ACTION_SEND);
            fileIntent.setType("ext/csv");
            fileIntent.putExtra(Intent.EXTRA_SUBJECT,"Search Jop App - Export Contacts");
            fileIntent.setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
            fileIntent.putExtra(Intent.EXTRA_STREAM,path);
            startActivity(Intent.createChooser(fileIntent,"send mail"));
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    public void exportOpportunites(View view) {
        StringBuilder data = new StringBuilder();
        //creation titres colonnes
        data.append("Contact,Detail,Avancement");
        //remplir data avec données DB
        for (Opportunite opportunite:listOpportunites){
            String string = "\n";
            string += opportunite.getContact().toString()+",";
            string += opportunite.getDetail()+",";
            string += opportunite.getAvancementOpportunite();
            data.append(string);
        }
        try {
            //sauvegarde du fichier
            FileOutputStream out = openFileOutput("export_opportunites.csv",MODE_PRIVATE);
            out.write(data.toString().getBytes());
            out.close();
            //export
            Context context = getApplicationContext();
            File fileLocation = new File(getFilesDir(),"export_opportunites.csv");
            Uri path = FileProvider.getUriForFile(context,"com.pouillos.myrechercheemploi.fileprovider",fileLocation);
            Intent fileIntent = new Intent(Intent.ACTION_SEND);
            fileIntent.setType("ext/csv");
            fileIntent.putExtra(Intent.EXTRA_SUBJECT,"Search Jop App - Export Opportunites");
            fileIntent.setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
            fileIntent.putExtra(Intent.EXTRA_STREAM,path);
            startActivity(Intent.createChooser(fileIntent,"send mail"));
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    public void exportRdvs(View view) {
        StringBuilder data = new StringBuilder();
        //creation titres colonnes
        data.append("Contact,Date,Type Rdv");
        //remplir data avec données DB
        for (Rdv rdv:listRdvs){
            String string = "\n";
            string += rdv.getContact().toString()+",";
            string += rdv.getDateString()+",";
            string += rdv.getTypeRdv();
            data.append(string);
        }
        try {
            //sauvegarde du fichier
            FileOutputStream out = openFileOutput("export_rdvs.csv",MODE_PRIVATE);
            out.write(data.toString().getBytes());
            out.close();
            //export
            Context context = getApplicationContext();
            File fileLocation = new File(getFilesDir(),"export_rdvs.csv");
            Uri path = FileProvider.getUriForFile(context,"com.pouillos.myrechercheemploi.fileprovider",fileLocation);
            Intent fileIntent = new Intent(Intent.ACTION_SEND);
            fileIntent.setType("ext/csv");
            fileIntent.putExtra(Intent.EXTRA_SUBJECT,"Search Jop App - Export Rdvs");
            fileIntent.setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
            fileIntent.putExtra(Intent.EXTRA_STREAM,path);
            startActivity(Intent.createChooser(fileIntent,"send mail"));
        } catch (Exception e){
            e.printStackTrace();
        }
    }



}
