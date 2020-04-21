package com.example.projet_laurin_marc.ui.mgmt;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.projet_laurin_marc.ui.MainActivity;
import com.example.projet_laurin_marc.R;
import com.example.projet_laurin_marc.database.entity.User;
import com.example.projet_laurin_marc.database.viewModel.UserViewModel;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.List;
import java.util.Locale;

public class LoginActivity extends AppCompatActivity {

    private UserViewModel userViewModel;
    private Button login;
    private Button register;
    private EditText etMail;
    private EditText etPwd;
    private String mailString;
    private String passString;

    private CheckBox remember;

    private TextView mStatusTextView;
    private TextView mDetailTextView;
    // [START declare_auth]
    private FirebaseAuth mAuth;
    // [END declare_auth]

    private FirebaseUser currentUser;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // check the language, set the one selecte throughout the app
        changeLanguage();
        // after language selection set the content
        setContentView(R.layout.activity_login);


        // [START initialize_auth]
        // Initialize Firebase Auth
        mAuth = FirebaseAuth.getInstance();
        // [END initialize_auth]



    // [END on_start_check_user]


        // get links
        login = findViewById(R.id.button_login);
        register = findViewById(R.id.button_createAccount);
        remember = findViewById(R.id.check_rememberMe);
        etMail = findViewById(R.id.text_email);
        etPwd = findViewById(R.id.text_pwd);

        // see if checkbox remember me is being applied, import data if yess
        remember();

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

                if (buttonView.isChecked()) {
                    // get the email that was remembered
                    SharedPreferences preferences = getSharedPreferences("checkbox", MODE_PRIVATE);
                    SharedPreferences.Editor editor = preferences.edit();

                    mailString = etMail.getText().toString();
                    editor.putString("email", mailString);

                    passString = etPwd.getText().toString();
                    editor.putString("password", passString);

                    editor.putString("remember", "true");
                    editor.apply();

                } else if (!buttonView.isChecked()) {
                    // do not remember
                    SharedPreferences preferences = getSharedPreferences("checkbox", MODE_PRIVATE);
                    SharedPreferences.Editor editor = preferences.edit();

                    editor.putString("remember", "false");
                    editor.apply();
                }
            }
        });

    }


    private void remember(){
        //check checkbox
        SharedPreferences preferences = getSharedPreferences("checkbox", MODE_PRIVATE);
        String checkbox = preferences.getString("remember", "defaul");
        if (checkbox.equals("true")) {
            remember.setChecked(true);
            mailString = preferences.getString("email", "default_mail");
            passString = preferences.getString("password", "default_password");
            etMail.setText(mailString);
            etPwd.setText(passString);

        } else {
            etMail.setText("");
            etPwd.setText("");
        }
    }


    public void login() {
        // get connection to database, viewmodel
        userViewModel = new ViewModelProvider(this).get(UserViewModel.class);
        userViewModel.getUsers().observe(this, new Observer<List<User>>() {
            @Override
            public void onChanged(List<User> users) {
                //get the infromation set in the from
                mailString = etMail.getText().toString();
                passString = etPwd.getText().toString();

                // check if all information are given
                if(passString.trim().isEmpty() && mailString.trim().isEmpty()){
                    etMail.setError(getString(R.string.error_fehler));
                    etPwd.setError(getString(R.string.error_fehler));
                    etMail.requestFocus();
                    return;
                }

                // if no users in database -> info to register!
                if (users == null) {
                    Toast.makeText(getApplicationContext(), (R.string.registration_first),
                            Toast.LENGTH_LONG).show();
                    return;
                }
                int value = 0;

                mAuth.signInWithEmailAndPassword(mailString, passString).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {
                                    // Sign in success, update UI with the signed-in user's information
                                    Log.d("signInWithEmail", "signInWithEmail:success");
                                    FirebaseUser user = mAuth.getCurrentUser();
                                    Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                                    //intent.putExtra("userId", users.get(i).getId()); //-> set userID and send to MainActivity..
                                    startActivity(intent);
                                    return;
                                } else {
                                    // If sign in fails, display a message to the user.
                                    Log.w("signInWithEmail", "signInWithEmail:failure", task.getException());

                                }

                            }
                        });
                //Looping to check inputs
                /*for (int i = 0; i < users.size(); i++) {
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
                    // wrong password
                    if (users.get(i).getEmail().equals(mailString)
                            && !(users.get(i).getPwd().equals(passString))){
                        etPwd.setError(getString(R.string.error_invalid_password));
                        etPwd.requestFocus();
                        etPwd.setText("");
                        return;
                    }
                    //wrong email
                    if(i >= users.size()-1 && !users.get(i).getEmail().equals(mailString) ){
                        value ++;
                        if(value > 0){
                            etMail.setError(getString(R.string.error_invalid_email));
                            etMail.requestFocus();
                            etPwd.setText("");
                            return;
                        }
                    }

                }*/
            }
        });
    }


    // to change the local language of the application
    public void changeLanguage() {
        SharedPreferences sharedPrefs = PreferenceManager.getDefaultSharedPreferences(this);
        String lang = sharedPrefs.getString("pref_lang", "de-rCH");

        Locale myLocale = new Locale(lang);
        Locale.setDefault(myLocale);
        Resources resources = getBaseContext().getResources();
        Configuration config = resources.getConfiguration();
        config.locale = myLocale;
        resources.updateConfiguration(config, resources.getDisplayMetrics());
    }

    //onBack Pressed do nothing,
    // so can not get back in the application with the backbutton once logged out!
    @Override
    public void onBackPressed() {
    }


}
