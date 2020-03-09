package com.example.projet_laurin_marc;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class RegistrationActivity extends AppCompatActivity {

    private Button button_register;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);

        // create backbutton in Actionbar
        //setupActionBar();

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
