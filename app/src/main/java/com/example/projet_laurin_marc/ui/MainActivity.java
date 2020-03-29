package com.example.projet_laurin_marc.ui;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.app.Application;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.Menu;
import android.view.MenuItem;

import com.example.projet_laurin_marc.R;
import com.example.projet_laurin_marc.database.entity.User;
import com.example.projet_laurin_marc.database.viewModel.UserViewModel;
import com.example.projet_laurin_marc.ui.mgmt.AboutActivity;
import com.example.projet_laurin_marc.ui.mgmt.LoginActivity;
import com.example.projet_laurin_marc.ui.mgmt.RegistrationActivity;
import com.example.projet_laurin_marc.ui.mgmt.SettingsActivity;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.List;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {
    // with AppCompatActivity you gard the Actionbar (Top) throughout the application!

    BottomNavigationView bottomNavigation;
    UserViewModel userViewModel;
    int userID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // ---------------   settings   -----------
        SharedPreferences sharedPrefs = PreferenceManager.getDefaultSharedPreferences(this);
        changeLanguage(sharedPrefs.getString("pref_lang", "de-rCH"));
        // ---------------   settings   -----------
        //get logged user, county => ResitenDetails; with or without changing options!
        userID = getIntent().getIntExtra("userId", 1111);
        getLoggedUserFromDB();

        bottomNavigation = findViewById(R.id.bottom_navigation);

        bottomNavigation.setOnNavigationItemSelectedListener(navListener);

        // Give the container the fragment that you want to start with when the menu opens!
        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new CantonFragment()).commit();
    }

    private BottomNavigationView.OnNavigationItemSelectedListener navListener =
            new BottomNavigationView.OnNavigationItemSelectedListener() {
                @Override
                public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                    Fragment selectedFragment = null;

                    switch (item.getItemId()) {
                        case R.id.nav_add:
                            Bundle bundle1 = new Bundle();
                            bundle1.putInt("userId1", userID);
                            //set Fragmentclass
                            selectedFragment = new AddFragment();
                            selectedFragment.setArguments(bundle1);
                            break;
                        case R.id.nav_cantons:
                            selectedFragment = new CantonFragment();
                            break;
                        case R.id.nav_search:
                            selectedFragment = new SearchFragment();
                            break;
                        case R.id.nav_profil:

                            Bundle bundle = new Bundle();
                            bundle.putInt("userId", userID);
                            //set Fragmentclass
                            selectedFragment = new ProfileFragment();
                            selectedFragment.setArguments(bundle);

                            break;
                    }
                    getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                            selectedFragment).commit();

                    return true;
                }
            };

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.top_actionbar, menu);
        return true;
    }

    // get access from fragments to this method to change titel in actionbar
    public void setActionBarTitle(String title) {
        getSupportActionBar().setTitle(title);
    }


    // ----------------------- Settings  --------------------------------------------
    @Override
    public void onResume(){
        super.onResume();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        switch(item.getItemId()){

            case R.id.action_about:

                Intent i = new Intent(MainActivity.this, AboutActivity.class);
                MainActivity.this.startActivity(i);

                break;
            case R.id.action_settings :

                Intent intent1 = new Intent(MainActivity.this, SettingsActivity.class);
                MainActivity.this.startActivity(intent1);
                break;

        }
        return true;
    }

    public void changeLanguage(String lang) {
        Locale myLocale = new Locale(lang);
        Locale.setDefault(myLocale);
        android.content.res.Configuration config = new android.content.res.Configuration();
        config.locale = myLocale;
        getBaseContext().getResources().updateConfiguration(config, getBaseContext().getResources().getDisplayMetrics());


        // is being used to change the language, display of welcome..
        //TextView welcome = (TextView) findViewById(R.id.main_txt_welcome);
        //welcome.setText(R.string.main_welcome);

    }

    public void getLoggedUserFromDB(){
        // get acces to database Users
        userViewModel = new ViewModelProvider(this).get(UserViewModel.class);
        // userViewModel.getUsers().
        userViewModel.getUsers().observe(this, new Observer<List<User>>() {
            @Override
            public void onChanged(List<User> users) {
                for (int i = 0; i < users.size(); i++){
                    if(users.get(i).getId() == userID){
                        String userCounty = users.get(i).getCounty();
                        PreferenceManager.getDefaultSharedPreferences(getBaseContext()).edit().putString("User_County", userCounty).apply();
                    }
                }
            }
        });
    }

}
