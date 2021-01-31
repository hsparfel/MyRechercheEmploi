package com.pouillos.myrechercheemploi.activities;


import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.google.android.material.chip.ChipGroup;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.textfield.TextInputEditText;
import com.pouillos.myrechercheemploi.R;
import com.pouillos.myrechercheemploi.activities.afficher.AfficherContactActivity;
import com.pouillos.myrechercheemploi.activities.afficher.AfficherListeContactsActivity;
import com.pouillos.myrechercheemploi.activities.afficher.AfficherListeOpportunitesActivity;
import com.pouillos.myrechercheemploi.activities.afficher.AfficherListeRdvsActivity;
import com.pouillos.myrechercheemploi.activities.afficher.AfficherListeSocietesActivity;
import com.pouillos.myrechercheemploi.activities.afficher.AfficherOpportuniteActivity;
import com.pouillos.myrechercheemploi.activities.afficher.AfficherRdvActivity;
import com.pouillos.myrechercheemploi.activities.afficher.AfficherSocieteActivity;


import com.pouillos.myrechercheemploi.activities.exporter.ExporterDatasActivity;
import com.pouillos.myrechercheemploi.dao.ContactDao;
import com.pouillos.myrechercheemploi.dao.DaoMaster;
import com.pouillos.myrechercheemploi.dao.DaoSession;

import com.pouillos.myrechercheemploi.dao.OpportuniteDao;
import com.pouillos.myrechercheemploi.dao.RdvDao;
import com.pouillos.myrechercheemploi.dao.SocieteDao;
import com.pouillos.myrechercheemploi.entities.Contact;
import com.pouillos.myrechercheemploi.fragments.DatePickerFragment;
import com.pouillos.myrechercheemploi.utils.DateUtils;

import org.greenrobot.greendao.database.Database;

import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.Executor;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import icepick.Icepick;

public class NavDrawerActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    //FOR DESIGN

    protected Toolbar toolbar;
    protected DrawerLayout drawerLayout;
    protected NavigationView navigationView;

    protected DaoSession daoSession;

    protected RdvDao rdvDao;
    protected SocieteDao societeDao;
    protected ContactDao contactDao;
    protected OpportuniteDao opportuniteDao;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //a redefinit à chq fois
        super.onCreate(savedInstanceState);
        //initialiser greenDAO
        initialiserDao();
        rdvDao = daoSession.getRdvDao();
        societeDao = daoSession.getSocieteDao();
        contactDao = daoSession.getContactDao();
        opportuniteDao = daoSession.getOpportuniteDao();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        //2 - Inflate the menu and add it to the Toolbar
        getMenuInflater().inflate(R.menu.menu_activity_main, menu);
        //getMenuInflater().inflate(R.menu.menu_add_item_to_db, menu);
        return true;
    }

    @Override
    public void onBackPressed() {
        // 5 - Handle back click to close menu
        if (this.drawerLayout.isDrawerOpen(GravityCompat.START)) {
            this.drawerLayout.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onNavigationItemSelected(MenuItem item) {

        // 4 - Handle Navigation Item Click
        int id = item.getItemId();
        Intent myProfilActivity;

        switch (id) {
            case R.id.activity_main_drawer_home:
                ouvrirActiviteSuivante(NavDrawerActivity.this, AccueilActivity.class,true);
                break;

            case R.id.activity_main_drawer_add_societe:
                //Toast.makeText(this, "à implementer", Toast.LENGTH_LONG).show();
                myProfilActivity = new Intent(NavDrawerActivity.this, AfficherSocieteActivity.class);
                startActivity(myProfilActivity);
                break;
            case R.id.activity_main_drawer_add_contact:
                //Toast.makeText(this, "à implementer", Toast.LENGTH_LONG).show();
                myProfilActivity = new Intent(NavDrawerActivity.this, AfficherContactActivity.class);
                startActivity(myProfilActivity);
                break;
            case R.id.activity_main_drawer_add_opportunite:
                //Toast.makeText(this, "à implementer", Toast.LENGTH_LONG).show();
                myProfilActivity = new Intent(NavDrawerActivity.this, AfficherOpportuniteActivity.class);
                startActivity(myProfilActivity);
                break;
            case R.id.activity_main_drawer_add_rdv:
                //Toast.makeText(this, "à implementer", Toast.LENGTH_LONG).show();
                myProfilActivity = new Intent(NavDrawerActivity.this, AfficherRdvActivity.class);
                startActivity(myProfilActivity);
                break;
            case R.id.activity_main_drawer_lister_societes:
                myProfilActivity = new Intent(NavDrawerActivity.this, AfficherListeSocietesActivity.class);
                startActivity(myProfilActivity);
                break;

            case R.id.activity_main_drawer_lister_contacts:
                myProfilActivity = new Intent(NavDrawerActivity.this, AfficherListeContactsActivity.class);
                startActivity(myProfilActivity);
                break;

            case R.id.activity_main_drawer_lister_opportunites:
                myProfilActivity = new Intent(NavDrawerActivity.this, AfficherListeOpportunitesActivity.class);
                startActivity(myProfilActivity);
                break;
            case R.id.activity_main_drawer_lister_rdvs:
                myProfilActivity = new Intent(NavDrawerActivity.this, AfficherListeRdvsActivity.class);
                startActivity(myProfilActivity);
                break;
            default:
                break;
        }

        this.drawerLayout.closeDrawer(GravityCompat.START);

        return true;
    }

    protected void raz() {
        contactDao.deleteAll();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Intent myProfilActivity; // = new Intent(NavDrawerActivity.this, ChercherContactActivity.class);
        //startActivity(myProfilActivity);
        //3 - Handle actions on menu items
        switch (item.getItemId()) {
            /*case R.id.menu_activity_main_params:
                Toast.makeText(this, "Il n'y a rien à paramétrer ici, passez votre chemin...", Toast.LENGTH_LONG).show();
                return true;*/
            case R.id.menu_activity_main_export_datas:
                //Toast.makeText(this, "Recherche indisponible, demandez plutôt l'avis de Google, c'est mieux et plus rapide.", Toast.LENGTH_LONG).show();
                myProfilActivity = new Intent(NavDrawerActivity.this, ExporterDatasActivity.class);
                startActivity(myProfilActivity);
                return true;



            default:
                return super.onOptionsItemSelected(item);
        }
    }
    // ---------------------
    // CONFIGURATION
    // ---------------------

    // 1 - Configure Toolbar
    public void configureToolBar() {
        this.toolbar = (Toolbar) findViewById(R.id.activity_main_toolbar);
        setSupportActionBar(toolbar);
    }

    // 2 - Configure Drawer Layout
    public void configureDrawerLayout() {
        this.drawerLayout = (DrawerLayout) findViewById(R.id.activity_main_drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();
    }

    // 3 - Configure NavigationView
    public void configureNavigationView() {
        this.navigationView = (NavigationView) findViewById(R.id.activity_main_nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }

    public void ouvrirActiviteSuivante(Context context, Class classe, boolean bool) {
        Intent intent = new Intent(context, classe);
        startActivity(intent);
        if (bool) {
            finish();
        }
    }

    public void revenirActivitePrecedente(String sharedName, String dataName, Long itemId) {
        SharedPreferences preferences=getSharedPreferences(sharedName,MODE_PRIVATE);
        SharedPreferences.Editor editor=preferences.edit();
        editor.putLong(dataName,itemId);
        editor.commit();
        finish();
    }

    public void ouvrirActiviteSuivante(Context context, Class classe, String nomExtra, Long objetIdExtra, boolean bool) {
        Intent intent = new Intent(context, classe);
        intent.putExtra(nomExtra, objetIdExtra);
        startActivity(intent);
        if (bool) {
            finish();
        }
    }

    public void ouvrirActiviteSuivante(Context context, Class classe, String nomExtra, String objetExtra, String nomExtra2, Long objetIdExtra2, boolean bool) {
        Intent intent = new Intent(context, classe);
        intent.putExtra(nomExtra, objetExtra);
        intent.putExtra(nomExtra2, objetIdExtra2);
        startActivity(intent);
        if (bool) {
            finish();
        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        Icepick.saveInstanceState(this, outState);
    }

    protected Date ActualiserDate(Date date, String time){
        Date dateActualisee = date;
        int nbHour = Integer.parseInt(time.substring(0,2));
        int nbMinute = Integer.parseInt(time.substring(3));
        dateActualisee = DateUtils.ajouterHeure(dateActualisee,nbHour);
        dateActualisee = DateUtils.ajouterMinute(dateActualisee,nbMinute);
        return dateActualisee;
    }

    protected <T> void buildDropdownMenu(List<T> listObj, Context context, AutoCompleteTextView textView) {
        List<String> listString = new ArrayList<>();
        String[] listDeroulante;
        listDeroulante = new String[listObj.size()];
        for (T object : listObj) {
            listString.add(object.toString());
        }
        listString.toArray(listDeroulante);
        ArrayAdapter adapter = new ArrayAdapter(context, R.layout.list_item, listDeroulante);
        textView.setAdapter(adapter);
    }

    @Override
    public Executor getMainExecutor() {
        return super.getMainExecutor();
    }

    protected boolean isChecked(ChipGroup chipGroup) {
        boolean bool;
        if (chipGroup.getCheckedChipId() != -1) {
            bool = true;
        } else {
            bool = false;
        }
        return bool;
    }

    protected boolean isFilled(TextInputEditText textInputEditText){
        boolean bool;
        if (textInputEditText.length()>0) {
            bool = true;
        } else {
            bool = false;
        }
        return bool;
    }

    protected boolean isFilled(AutoCompleteTextView textView){
        boolean bool;
        if (textView != null) {
            if (!textView.getText().toString().equalsIgnoreCase("")) {
                bool = true;
            } else {
                bool = false;
            }
        } else {
            bool = false;
        }
        return bool;
    }

    protected boolean isFilled(Object object){
        boolean bool;
        if (object!=null) {
            bool = true;
        } else {
            bool = false;
        }
        return bool;
    }

    protected boolean isValidTel(TextView textView) {
        if (!TextUtils.isEmpty(textView.getText()) && textView.getText().length() <10) {
            return false;
        } else {
            return true;
        }
    }

    protected boolean isValidZip(TextView textView) {
        if (!TextUtils.isEmpty(textView.getText()) && textView.getText().length() <5) {
            return false;
        } else {
            return true;
        }
    }

    protected boolean isEmailAdress(String email){
        Pattern p = Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,4}$");
        Matcher m = p.matcher(email.toUpperCase());
        return m.matches();
    }
    protected boolean isValidEmail(TextView textView) {
        if (!TextUtils.isEmpty(textView.getText()) && !isEmailAdress(textView.getText().toString())) {
            return false;
        } else {
            return true;
        }
    }

    protected static float floatArrondi(float number, int decimalPlace) {
        BigDecimal bd = new BigDecimal(number);
        bd = bd.setScale(decimalPlace, BigDecimal.ROUND_HALF_UP);
        return bd.floatValue();
    }

    public DaoSession getDaoSession() {
        return daoSession;
    }

    public void showDatePickerDialog(View v,TextInputEditText textView, boolean hasDateMin, boolean hasDateMax,Date dateMin,Date dateMax) {
        DatePickerFragment newFragment = new DatePickerFragment();
        newFragment.show(getSupportFragmentManager(), "buttonDate");
        newFragment.setOnDateClickListener(new DatePickerFragment.onDateClickListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
                if (hasDateMin) {
                    datePicker.setMinDate(dateMin.getTime());
                }
                if (hasDateMax) {
                    datePicker.setMaxDate(dateMax.getTime());
                }

                String dateJour = ""+datePicker.getDayOfMonth();
                String dateMois = ""+(datePicker.getMonth()+1);
                String dateAnnee = ""+datePicker.getYear();
                if (datePicker.getDayOfMonth()<10) {
                    dateJour = "0"+dateJour;
                }
                if (datePicker.getMonth()+1<10) {
                    dateMois = "0"+dateMois;
                }
                String dateString = dateJour+"/"+dateMois+"/"+dateAnnee;

                textView.setText(dateString);
            }
        });
    }

    public Date convertStringToDate(String dateString) {
        DateFormat df = new SimpleDateFormat(getResources().getString(R.string.format_date));
        Date dateToReturn = new Date();
        try{
                dateToReturn = df.parse(dateString);
             }catch(ParseException e){
                 System.out.println(getResources().getString(R.string.error));
            }
        return dateToReturn;
    }

    public void initialiserDao() {
        DaoMaster.DevOpenHelper helper = new DaoMaster.DevOpenHelper(this, "my_recherche_emploi_DB", null);
        Database db = helper.getWritableDb();
        DaoMaster daoMaster = new DaoMaster(db);
        daoSession = daoMaster.newSession();
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        View view = getCurrentFocus();
        if (view != null && (ev.getAction() == MotionEvent.ACTION_UP || ev.getAction() == MotionEvent.ACTION_MOVE) && view instanceof EditText && !view.getClass().getName().startsWith("android.webkit.")) {
            int scrcoords[] = new int[2];
            view.getLocationOnScreen(scrcoords);
            float x = ev.getRawX() + view.getLeft() - scrcoords[0];
            float y = ev.getRawY() + view.getTop() - scrcoords[1];
            if (x < view.getLeft() || x > view.getRight() || y < view.getTop() || y > view.getBottom())
                ((InputMethodManager)this.getSystemService(Context.INPUT_METHOD_SERVICE)).hideSoftInputFromWindow((this.getWindow().getDecorView().getApplicationWindowToken()), 0);
        }
        return super.dispatchTouchEvent(ev);
    }
}
