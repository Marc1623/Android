package com.example.projet_laurin_marc.ui.mgmt;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Display;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.projet_laurin_marc.ui.MainActivity;
import com.example.projet_laurin_marc.R;
import com.example.projet_laurin_marc.database.entity.User;
import com.example.projet_laurin_marc.database.viewModel.UserViewModel;
import com.example.projet_laurin_marc.ui.ProfileFragment;

import java.util.List;

public class LoginActivity extends AppCompatActivity {

    UserViewModel userViewModel;
    private Button login;
    private Button register;
    private EditText etMail;
    private EditText etPwd;
    private String mailString;
    private String passString;

    private CheckBox remember;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        login = findViewById(R.id.button_login);
        register = findViewById(R.id.button_createAccount);
        remember = findViewById(R.id.check_rememberMe);

        etMail = findViewById(R.id.text_email);
        etPwd = findViewById(R.id.text_pwd);

        //check checkbox
        SharedPreferences preferences = getSharedPreferences("checkbox", MODE_PRIVATE);
        String checkbox = preferences.getString("remember", "defaul");
        if(checkbox.equals("true")){
            mailString = preferences.getString("email", "default_mail");
            passString = preferences.getString("password", "default_password");
            etMail.setText(mailString);
            etPwd.setText(passString);
        }

        //Login
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                login();
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

        remember.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                if(buttonView.isChecked()){
                    SharedPreferences preferences = getSharedPreferences("checkbox", MODE_PRIVATE);
                    SharedPreferences.Editor editor = preferences.edit();
                    editor.putString("remember", "true");
                    mailString = etMail.getText().toString();
                    editor.putString("email", mailString);
                    System.out.println("1mail: " + mailString);
                    passString = etPwd.getText().toString();
                    editor.putString("password", passString);
                    System.out.println("1pwd: " + passString);
                    editor.apply();
                }
                else if (!buttonView.isChecked()){
                    SharedPreferences preferences = getSharedPreferences("checkbox", MODE_PRIVATE);
                    SharedPreferences.Editor editor = preferences.edit();
                    editor.putString("remember", "false");
                    editor.apply();
                }
            }
        });

    }


    public void login() {
        userViewModel = new ViewModelProvider(this).get(UserViewModel.class);
        userViewModel.getUsers().observe(this, new Observer<List<User>>() {
            @Override
            public void onChanged(List<User> users) {
                mailString = etMail.getText().toString();
                passString = etPwd.getText().toString();


                if (users == null)
                    return;

                int nr = users.size();
                //Looping to check inputs
                for (int i = 0; i < nr; i++) {
                    if (users.get(i).getEmail().equals(mailString)
                            && users.get(i).getPwd().equals(passString)) {

                        Toast.makeText(getApplicationContext(), (R.string.login_successful),
                                Toast.LENGTH_LONG).show();

                        //switch activity
                        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                        intent.putExtra("userId", users.get(i).getId()); //-> set userID and send to MainActivity..
                        startActivity(intent);
                        return;
                    }
                   /* if (!users.get(i).getEmail().equals(mailString) && i == users.size()-1) {
                        etMail.setError(getString(R.string.error_invalid_email));
                        etMail.requestFocus();
                        etMail.setText("");
                    }
                    if (users.get(i).getEmail().equals(mailString) && !users.get(i).getPwd().equals(passString) && i == users.size()-1) {
                        etPwd.setError(getString(R.string.error_pwd_wrong));
                        etPwd.requestFocus();
                        etPwd.setText("");
                    }*/
                }
            }
        });
    }

    public void onCheckboxClicked(View view) {
        // Is the view now checked?
        boolean checked = ((CheckBox) view).isChecked();

        // Check which checkbox was clicked
        switch (view.getId()) {
            case R.id.check_rememberMe:
                if (checked) {
                    System.out.println("checked");

                } else

                    break;
        }
    }
}
