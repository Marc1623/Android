package com.example.projet_laurin_marc;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;


import com.example.projet_laurin_marc.static_database.County;
import com.example.projet_laurin_marc.static_database.DataBaseHelper;


import java.io.File;

import java.util.ArrayList;
import java.util.List;

public class RegistrationActivity extends AppCompatActivity {

    private Button button_register;
    DataBaseHelper dbHelper;
    List<County> coutylist;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);

        // spinner cantons to choose canton
        Spinner sp = (Spinner) findViewById(R.id.spinner_cantons);
        // Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,R.array.cantons_array, android.R.layout.simple_spinner_item);
        //specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // apply the adapter to the spinner
        sp.setAdapter(adapter);

        // if canton was choosen then get the list of counties in the Spinner
        // TODO: 09.03.2020  list of counties
        coutylist = new ArrayList<>();
        coutylist.clear();

        dbHelper = new DataBaseHelper(this);
        //check if db exists
        File database = getApplicationContext().getDatabasePath(DataBaseHelper.DATABASE_NAME);
        if(database.exists() == false){
            dbHelper.getReadableDatabase();
            //copy
            if(dbHelper.copyDataBase()){
                Toast.makeText(this, "Copy database success", Toast.LENGTH_SHORT).show();
            }else {
                Toast.makeText(this, "Copy db error",Toast.LENGTH_SHORT).show();
                return;
            }
        }
        // get coutylist
        coutylist = dbHelper.getListCounties();

        // spinner cantons to choose canton
        Spinner sp2 = (Spinner) findViewById(R.id.spinner_county);
        // Create an ArrayAdapter using the database and a default spinner layout
        ArrayAdapter<County> adapter2 = new ArrayAdapter<County>(this, android.R.layout.simple_spinner_item, coutylist);
        //specify the layout to use when the list of choices appears
        adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // apply the adapter to the spinner
        sp2.setAdapter(adapter2);

        button_register = (Button) findViewById(R.id.button_register);

        button_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // get email and Password from view
                EditText mail1 = (EditText) findViewById(R.id.text_email);
                EditText mail2 = (EditText) findViewById(R.id.text_checkemail);
                EditText pwd1 = (EditText) findViewById(R.id.text_pwd);
                EditText pwd2 = (EditText) findViewById(R.id.text_checkpwd);
                /*
                if (mail1.equals(mail2))
                    if (pwd1.equals(pwd2))
                        else{

                }
                */


                // TODO: 09.03.2020 Check registration data, when ok move on the menu give msg
                //does the email already exist? .. make tests and give notifications to the user!


                // give msg (Pop-Up), that login was successful
                Toast.makeText(RegistrationActivity.this, "Registration complete", Toast.LENGTH_LONG).show();

                Intent intent = new Intent(RegistrationActivity.this, LoginActivity.class);
                RegistrationActivity.this.startActivity(intent);
            }
        });

    }//end onCreate


}
