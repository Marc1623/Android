package com.example.projet_laurin_marc.ui.mgmt;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.projet_laurin_marc.R;
import com.example.projet_laurin_marc.database.async.user.CreateUser;
import com.example.projet_laurin_marc.database.entity.User;
import com.example.projet_laurin_marc.database.util.OnAsyncEventListener;
import com.example.projet_laurin_marc.database.viewModel.UserViewModel;
import com.example.projet_laurin_marc.static_database.County1;
import com.example.projet_laurin_marc.static_database.DataBaseHelper;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class RegistrationActivity extends AppCompatActivity {

    private Button button_register;
    private Spinner spCanton;
    private Spinner spCounty;
    private DataBaseHelper dbHelper;
    private List<County1> countyList;

    private EditText etMail1;
    private EditText etMail2;
    private EditText etPwd1;
    private EditText etPwd2;

    private String selectedCanton;

    boolean value = true;

    private static final String TAG = "RegisterActivity";

    private UserViewModel vm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);

        spCantons();
        initializeFrom();
    }//end onCreate


    private void initializeFrom() {
        // get email and Password from view
        etMail1 = findViewById(R.id.text_email);
        etMail2 = findViewById(R.id.text_checkemail);
        etPwd1 = findViewById(R.id.text_pwd);
        etPwd2 = findViewById(R.id.text_checkpwd);

        button_register = (Button) findViewById(R.id.button_register);
        button_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (saveUser()) {
                    // give msg (Pop-Up), that login was successful
                    Toast.makeText(RegistrationActivity.this, R.string.registration_complete, Toast.LENGTH_LONG).show();
                    // redirect to login
                    Intent intent = new Intent(RegistrationActivity.this, LoginActivity.class);
                    RegistrationActivity.this.startActivity(intent);
                }
                ;
            }
        });
    }

    // data from String array in strings.xml
    private void spCantons() {
        // spinner cantons to choose canton
        spCanton = (Spinner) findViewById(R.id.spinner_cantons);

        // Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.cantons_array, android.R.layout.simple_spinner_item);
        //specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // apply the adapter to the spinner
        spCanton.setAdapter(adapter);

        // check which canton is selected and give corresponding countylist
        spCanton.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                selectedCanton = (String) spCanton.getItemAtPosition(position); // save selection in variable
                spCounties();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    // data from static database
    public void spCounties() {
        // create instance of list
        countyList = new ArrayList<>();
        countyList.clear();

        // create connection to static db
        dbHelper = new DataBaseHelper(this);

        //create the db, load into cellphone
        try {
            dbHelper.createDatabase();
        } catch (IOException e) {
            e.printStackTrace();
        }

        // get countylist
        countyList = dbHelper.getCountiesByCanton(selectedCanton); // selected canton from spinner Canton to make selection of counties
        // spinner
        spCounty = (Spinner) findViewById(R.id.spinner_county);
        // Create an ArrayAdapter using the database and a default spinner layout
        ArrayAdapter<County1> adapter2 = new ArrayAdapter<County1>(this, android.R.layout.simple_spinner_item, countyList);
        //specify the layout to use when the list of choices appears
        adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // apply the adapter to the spinner
        spCounty.setAdapter(adapter2);
    }

    public boolean saveUser() {
        // get user inputs
        String mailString = etMail1.getText().toString();
        String mail2String = etMail2.getText().toString();
        String pwdString = etPwd1.getText().toString();
        String pwd1String = etPwd2.getText().toString();

        // check if input fields are empty, if yes return
        if (pwdString.trim().isEmpty() && mail2String.trim().isEmpty() && pwdString.trim().isEmpty() &&
                pwd1String.trim().isEmpty()) {
            etMail1.setError(getString(R.string.error_fehler));
            etMail2.setError(getString(R.string.error_fehler));
            etPwd1.setError(getString(R.string.error_fehler));
            etPwd2.setError(getString(R.string.error_fehler));
            etMail1.requestFocus();

            Toast.makeText(getApplicationContext(), R.string.error_enter_all_information,
                    Toast.LENGTH_LONG).show();
            return false;
        }

        // check if mail inputs are correct
        if (!mailString.equals(mail2String) || !android.util.Patterns.EMAIL_ADDRESS.matcher(mailString).matches()) {
            etMail1.setError(getString(R.string.error_invalid_email));
            etMail1.requestFocus();
            value = false;
        }

        // check pwd length and if they're identical
        if (!pwdString.equals(pwd1String) || pwdString.length() < 5) {
            etPwd1.setError(getString(R.string.error_invalid_password));
            etPwd1.requestFocus();
            etPwd1.setText("");
            etPwd2.setText("");
            value = false;
        }

        // get values from spinners no check needed as there is always a selection
        String cantonString = spCanton.getSelectedItem().toString();
        String countyString = spCounty.getSelectedItem().toString();

        User user = new User(mailString, pwdString, countyString);

        vm.createUser(user, new OnAsyncEventListener() {
            @Override
            public void onSuccess() {
                Log.d(TAG, "createUserWithEmail: success");
                value = true;
            }

            @Override
            public void onFailure(Exception e) {
                Log.d(TAG, "createUserWithEmail: failure", e);
                value = false;
            }
        });

        return value;
    }
}
