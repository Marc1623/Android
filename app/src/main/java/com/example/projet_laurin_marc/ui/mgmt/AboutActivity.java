package com.example.projet_laurin_marc.ui.mgmt;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import androidx.appcompat.app.AppCompatActivity;

import com.example.projet_laurin_marc.R;


public class AboutActivity extends Activity {

    /*
    problem why it did not work:
        1.) the setupActionBar() threw an Exception
        2.) if you want to have the default actionbar on the top you nee to have extends AppCompatActivity,
        you had "Activity"*/


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);
        // Show the Up button in the action bar.
        //setupActionBar();
    }
/*
    private void setupActionBar() {

        getActionBar().setDisplayHomeAsUpEnabled(true);

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                Intent intent = new Intent(AboutActivity.this, MainActivity.class);
                AboutActivity.this.startActivity(intent);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }*/
}

