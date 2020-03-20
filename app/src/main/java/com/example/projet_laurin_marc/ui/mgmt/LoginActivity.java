package com.example.projet_laurin_marc.ui.mgmt;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.projet_laurin_marc.ui.MainActivity;
import com.example.projet_laurin_marc.R;
import com.example.projet_laurin_marc.database.entity.User;
import com.example.projet_laurin_marc.database.viewModel.UserViewModel;

import java.util.List;

public class LoginActivity extends AppCompatActivity {

    UserViewModel userViewModel;
    private Button login;
    private Button register;
    private EditText etMail;
    private EditText etPwd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        login = findViewById(R.id.button_login);
        register = findViewById(R.id.button_createAccount);

        etMail = findViewById(R.id.text_email);
        etPwd = findViewById(R.id.text_pwd);

        //Login
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                login();
                System.out.println("Click");
            }
        });

        //Register
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent inent = new Intent(LoginActivity.this, RegistrationActivity.class);
                startActivity(inent);
            }
        });

    }

    //Login Method
    public void login() {

        userViewModel = new ViewModelProvider(this).get(UserViewModel.class);
        userViewModel.getUsers().observe(this, new Observer<List<User>>() {
            @Override
            public void onChanged(List<User> users) {
                String mailString = etMail.getText().toString();
                String passString = etPwd.getText().toString();

                int nr = userViewModel.getUsers().getValue().size();
                //Looping to check inputs
                for (int i = 0; i < nr; i++) {
                    if (userViewModel.getUsers().getValue().get(i).getEmail().equals(mailString)
                            && userViewModel.getUsers().getValue().get(i).getPwd().equals(passString)) {

                        Toast.makeText(getApplicationContext(), "Login successful",
                                Toast.LENGTH_LONG).show();

                        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                        intent.putExtra("userId", users.get(i).getId());
                        startActivity(intent);

                    }
                    if (!userViewModel.getUsers().getValue().get(i).getEmail().equals(mailString)) {
                        Toast.makeText(getApplicationContext(), "E-Mail does not exist",
                                Toast.LENGTH_LONG).show();
                        etMail.setTextColor(Color.RED);
                    }
                    if (userViewModel.getUsers().getValue().get(i).getEmail().equals(mailString) && !userViewModel.getUsers().getValue().get(i).getPwd().equals(passString)) {
                        Toast.makeText(getApplicationContext(), "Wrong password",
                                Toast.LENGTH_LONG).show();
                        etPwd.setTextColor(Color.RED);
                    }
                }
            }
        });


    }}
