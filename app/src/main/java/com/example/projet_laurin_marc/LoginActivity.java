package com.example.projet_laurin_marc;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import javax.security.auth.login.LoginException;

public class LoginActivity extends AppCompatActivity {

    private Button button_login;
    private Button button_createAccount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        // Set title bar


        button_login = (Button) findViewById(R.id.button_login);
        button_createAccount = (Button) findViewById(R.id.button_createAccount);

        button_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // get email and Password from view
                EditText Email = (EditText) findViewById(R.id.text_email);
                EditText postcode = (EditText) findViewById(R.id.text_pwd);

                // TODO: 09.03.2020 Check login data, when ok move on the menu give msg


                // give msg (Pop-Up), that login was successful
                Toast.makeText(LoginActivity.this, "Access granted", Toast.LENGTH_LONG).show();

                Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                LoginActivity.this.startActivity(intent);
            }
        });

        button_createAccount.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // open view for registration
                Intent intent = new Intent(LoginActivity.this, RegistrationActivity.class);
                LoginActivity.this.startActivity(intent);
            }
        });
    }


}
