package com.example.projet_laurin_marc;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends  AppCompatActivity {
    // with AppCompatActivity you gard the Actionbar (Top) throughout the application!

    BottomNavigationView bottomNavigation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        bottomNavigation = findViewById(R.id.bottom_navigation);

        bottomNavigation.setOnNavigationItemSelectedListener(navListener);

        // Give the container the fragment that you want to start with when the menu opens!
        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new CantonFragment()).commit();
    }

    private BottomNavigationView.OnNavigationItemSelectedListener navListener =
               new BottomNavigationView.OnNavigationItemSelectedListener() {
                @Override public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                    Fragment selectedFragment = null;

                    switch (item.getItemId()) {
                        case R.id.nav_add:
                            selectedFragment = new AddFragment();
                           break;
                        case R.id.nav_cantons:
                            selectedFragment = new CantonFragment();
                            break;
                        case R.id.nav_search:
                            selectedFragment = new SearchFragment();
                            break;
                        case R.id.nav_profil:
                            selectedFragment = new ProfileFragment();
                            break;
                    }
                    getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                            selectedFragment).commit();

                    return true;
                }
            };

}
