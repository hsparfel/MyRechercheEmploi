package com.pouillos.myrechercheemploi.activities.afficher;

import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AutoCompleteTextView;
import android.widget.DatePicker;
import android.widget.ProgressBar;
import android.widget.TimePicker;

import androidx.annotation.RequiresApi;
import androidx.appcompat.widget.Toolbar;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.pouillos.myrechercheemploi.R;
import com.pouillos.myrechercheemploi.activities.AccueilActivity;
import com.pouillos.myrechercheemploi.activities.NavDrawerActivity;
import com.pouillos.myrechercheemploi.activities.fragments.DatePickerFragmentDateJour;
import com.pouillos.myrechercheemploi.entities.Contact;
import com.pouillos.myrechercheemploi.entities.Rdv;
import com.pouillos.myrechercheemploi.enumeration.TypeRdv;
import com.pouillos.myrechercheemploi.utils.DateUtils;

import java.io.Serializable;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import icepick.Icepick;
import icepick.State;

public class AfficherRdvActivity extends NavDrawerActivity implements Serializable, AdapterView.OnItemClickListener{

    @BindView(R.id.fabSave)
    FloatingActionButton fabSave;
    @BindView(R.id.fabDelete)
    FloatingActionButton fabDelete;

    Rdv rdv;
    TypeRdv typeRdv;
    Date date;
    TimePickerDialog picker;

    Contact contactSelected;
    List<Contact> listContactsBD;
    
    @BindView(R.id.selectContact)
    AutoCompleteTextView selectedContact;
    @BindView(R.id.listContacts)
    TextInputLayout listContacts;

    @State
    TypeRdv typeRdvSelected;
    List<TypeRdv> listTypeRdvsBD;
    
    @BindView(R.id.selectTypeRdv)
    AutoCompleteTextView selectedTypeRdv;
    @BindView(R.id.listTypeRdvs)
    TextInputLayout listTypeRdvs;


    @BindView(R.id.textDate)
    TextInputEditText textDate;
    @BindView(R.id.layoutDate)
    TextInputLayout layoutDate;

    @BindView(R.id.textHeure)
    TextInputEditText textHeure;
    @BindView(R.id.layoutHeure)
    TextInputLayout layoutHeure;

    Rdv currentRdv;

    @BindView(R.id.my_progressBar)
    ProgressBar progressBar;
    @BindView(R.id.activity_main_toolbar)
    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Icepick.restoreInstanceState(this, savedInstanceState);
        setContentView(R.layout.activity_afficher_rdv);

        ButterKnife.bind(this);

        this.configureToolBar();
        this.configureDrawerLayout();
        this.configureNavigationView();

        progressBar.setVisibility(View.VISIBLE);

        AfficherRdvActivity.AsyncTaskRunner runner = new AfficherRdvActivity.AsyncTaskRunner();
        runner.execute();

        fabDelete.hide();



        selectedContact.setOnItemClickListener(this);
    }

    private void traiterIntent() {
        Intent intent = getIntent();
        if (intent.hasExtra("rdvId")) {
            fabDelete.show();
            Long rdvId = intent.getLongExtra("rdvId", 0);
            currentRdv = rdvDao.load(rdvId);
            //textDetail.setText(currentRdv.getDetail());
            contactSelected = currentRdv.getContact();
            int position = 0;
            for (Contact contact : listContactsBD) {
                if (contact != contactSelected) {
                    position ++;
                } else {
                    break;
                }
            }
            selectedContact.setText(contactSelected.toString(),false);
            typeRdvSelected = currentRdv.getTypeRdv();
            selectedTypeRdv.setText(typeRdvSelected.toString(),false);
            textDate.setText(DateUtils.ecrireDate(currentRdv.getDate()));
            DateFormat df = new SimpleDateFormat("dd/MM/yy");
            try{
                date = df.parse(DateUtils.ecrireDate(currentRdv.getDate()));
            }catch(ParseException e){
                System.out.println("ERROR");
            }
            textHeure.setText(DateUtils.ecrireHeure(currentRdv.getDate()));
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(date);
            calendar.add(Calendar.HOUR_OF_DAY, Integer.parseInt(DateUtils.ecrireJusteHeure(currentRdv.getDate())));
            calendar.add(Calendar.MINUTE, Integer.parseInt(DateUtils.ecrireJusteMinute(currentRdv.getDate())));
            date = calendar.getTime();
        }
    }


    @OnClick(R.id.fabSave)
    public void setFabSaveClick() {
        if (isFullRempli()) {
            if (currentRdv == null) {
                rdv = new Rdv();
            } else {
                rdv = currentRdv;
            }
            rdv.setTypeRdv(typeRdv);
            rdv.setContact(contactSelected);
            rdv.setTypeRdv(TypeRdv.rechercheParNom(selectedTypeRdv.getText().toString()));
            rdv.setDate(date);
            rdv.setDateString(DateUtils.ecrireDateHeure(date));

            if (currentRdv == null) {
                rdvDao.insert(rdv);
            } else {
                rdvDao.update(rdv);
            }
            ouvrirActiviteSuivante(AfficherRdvActivity.this, AccueilActivity.class,false);
        }
    }

    @OnClick(R.id.fabDelete)
    public void setFabDeleteClick() {
        if (currentRdv != null) {
            rdvDao.delete(currentRdv);
        }
        ouvrirActiviteSuivante(AfficherRdvActivity.this, AccueilActivity.class,false);
    }

    private boolean isFullRempli() {
        boolean bool = true;

        if (!isFilled(selectedContact)) {
            bool = false;
            Snackbar.make(selectedContact, "Veuillez Selectionner une contact", Snackbar.LENGTH_SHORT).setAnchorView(selectedTypeRdv).show();
        }
        if (!isFilled(selectedTypeRdv)) {
            bool = false;
            Snackbar.make(selectedTypeRdv, "Veuillez Selectionner un avancement d'rdv", Snackbar.LENGTH_SHORT).setAnchorView(fabSave).show();
        }

        if (!isFilled(textDate)) {
            bool = false;
            layoutDate.setError("Obligatoire");
        }
        if (!isFilled(textHeure)) {
            bool = false;
            layoutHeure.setError("Obligatoire");
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
            listTypeRdvsBD = new ArrayList<TypeRdv>();
            listTypeRdvsBD.addAll(new ArrayList<TypeRdv>(Arrays.asList(TypeRdv.values())));
            listTypeRdvsBD.remove(0);
            publishProgress(100);
            return null;
        }

        @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
        protected void onPostExecute(Void result) {
            progressBar.setVisibility(View.GONE);
            if (listTypeRdvsBD.size() !=0) {
                buildDropdownMenu(listTypeRdvsBD, AfficherRdvActivity.this,selectedTypeRdv);
            }
            if (listContactsBD.size() == 0) {
                Snackbar.make(selectedContact, "Pas de contacts en BD", Snackbar.LENGTH_SHORT).setAnchorView(selectedContact).show();
            } else {
                buildDropdownMenu(listContactsBD, AfficherRdvActivity.this,selectedContact);

                traiterIntent();
            }
        }

        @RequiresApi(api = Build.VERSION_CODES.N)
        protected void onProgressUpdate(Integer... integer) {
            progressBar.setProgress(integer[0],true);
        }
    }


    public void showTimePickerDialog(View v) {
        final Calendar cldr = Calendar.getInstance();
        int hour = 8;
        int minutes = 0;
        // time picker dialog
        picker = new TimePickerDialog(AfficherRdvActivity.this,
                new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker tp, int sHour, int sMinute) {
                        String hour = "";
                        String minute = "";
                        if (sHour<10){
                            hour+="0";
                        }
                        if (sMinute<10){
                            minute+="0";
                        }
                        hour+=sHour;
                        minute+=sMinute;

                        textHeure.setText(hour + ":" + minute);
                        Calendar calendar = Calendar.getInstance();
                        calendar.setTime(date);
                        calendar.add(Calendar.HOUR_OF_DAY, sHour);
                        calendar.add(Calendar.MINUTE, sMinute);
                        date = calendar.getTime();
                    }
                }, hour, minutes, true);
        picker.show();
    }


    public void showDatePickerDialog(View v) {
        DatePickerFragmentDateJour newFragment = new DatePickerFragmentDateJour();
        //newFragment.show(getSupportFragmentManager(), "buttonDate");
        newFragment.show(getSupportFragmentManager(), "editTexteDate");
        newFragment.setOnDateClickListener(new DatePickerFragmentDateJour.onDateClickListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
                datePicker.setMinDate(new Date().getTime());
                // TextView tv1= (TextView) findViewById(R.id.textDate);
                String dateJour = ""+datePicker.getDayOfMonth();
                String dateMois = ""+(datePicker.getMonth()+1);
                String dateAnnee = ""+datePicker.getYear();
                if (datePicker.getDayOfMonth()<10) {
                    dateJour = "0"+dateJour;
                }
                if (datePicker.getMonth()+1<10) {
                    dateMois = "0"+dateMois;
                }
                Calendar c1 = Calendar.getInstance();
                // set Month
                // MONTH starts with 0 i.e. ( 0 - Jan)
                c1.set(Calendar.MONTH, datePicker.getMonth());
                // set Date
                c1.set(Calendar.DATE, datePicker.getDayOfMonth());
                // set Year
                c1.set(Calendar.YEAR, datePicker.getYear());
                // creating a date object with specified time.
                date = c1.getTime();

                String dateString = dateJour+"/"+dateMois+"/"+dateAnnee;
                //tv1.setText("date: "+dateString);
                textDate.setText(dateString);
                textDate.setError(null);
                DateFormat df = new SimpleDateFormat("dd/MM/yy");
                try{
                    date = df.parse(dateString);
                }catch(ParseException e){
                    System.out.println("ERROR");
                }
            }
        });
    }
}
