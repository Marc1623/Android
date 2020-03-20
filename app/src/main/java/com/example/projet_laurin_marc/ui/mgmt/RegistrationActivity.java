package com.example.projet_laurin_marc.ui.mgmt;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;


import com.example.projet_laurin_marc.R;
import com.example.projet_laurin_marc.database.entity.User;
import com.example.projet_laurin_marc.database.viewModel.UserViewModel;
import com.example.projet_laurin_marc.static_database.County;
import com.example.projet_laurin_marc.static_database.DataBaseHelper;


import java.io.File;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class RegistrationActivity extends AppCompatActivity {

    private Button button_register;
    private Spinner spCanton;
    private Spinner spCounty;
    private DataBaseHelper dbHelper;
    private List<County> countyList;

    private EditText etMail1;
    private EditText etMail2;
    private EditText etPwd1;
    private EditText etPwd2;

    private UserViewModel vm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);

        spCantons();
        spCounties();
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

                if(saveUser()){
                    // give msg (Pop-Up), that login was successful
                    Toast.makeText(RegistrationActivity.this, "Registration complete", Toast.LENGTH_LONG).show();

                    Intent intent = new Intent(RegistrationActivity.this, LoginActivity.class);
                    RegistrationActivity.this.startActivity(intent);
                };
            }
        });
    }

    private void spCantons() {
        // spinner cantons to choose canton
        spCanton = (Spinner) findViewById(R.id.spinner_cantons);

        // Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.cantons_array, android.R.layout.simple_spinner_item);
        //specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // apply the adapter to the spinner
        spCanton.setAdapter(adapter);
/*
        spCanton.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selectedValue = parent.getSelectedItem().toString();

                switch (selectedValue){
                    case "Aargau":
                            spCounties("AG");
                        break;
                    case "Appenzell Ausserrhoden":
                        spCounties("AG");
                        break;
                    case "Appenzell Innerrhoden":
                        spCounties("AG");
                        break;
                    case "Basel-Landschaft":
                        spCounties("AG");
                        break;
                    case "Basel-Stadt":
                        spCounties("AG");
                        break;
                    case "Bern":
                        spCounties("AG");
                        break;
                    case "Freiburg":
                        spCounties("AG");
                        break;
                    case "Genf":
                        spCounties("AG");
                        break;
                    case "Glarus":
                        spCounties("AG");
                        break;
                    case "Graubünden":
                        spCounties("AG");
                        break;
                    case "Jura":
                        spCounties("AG");
                        break;
                    case "Neuenburg":
                        spCounties("AG");
                        break;
                    case "Nidwalden":
                        spCounties("AG");
                        break;
                    case "Obwalden":
                        spCounties("AG");
                        break;
                    case "Schaffhausen":
                        spCounties("AG");
                        break;
                    case "Schwyz":
                        spCounties("AG");
                        break;
                    case "Solothurn":
                        spCounties("AG");
                        break;
                    case "St. Gallen":
                        spCounties("AG");
                        break;
                    case "Tessin":
                        spCounties("AG");
                        break;
                    case "Thurgau":
                        spCounties("AG");
                        break;
                    case "Uri":
                        spCounties("AG");
                        break;
                    case "Waadt":
                        spCounties("AG");
                        break;
                    case "Wallis":
                        spCounties("AG");
                        break;
                    case "Zug":
                        spCounties("AG");
                        break;
                    case "Zürich":
                        spCounties("AG");
                        break;
                    default:
                        break;
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });*/

    }

    public void spCounties(/*String selectedCantonAbb*/) {
        // if canton was choosen then get the list of counties in the Spinner
        // TODO: 19.03.2020 filter counties by cantons..
        countyList = new ArrayList<>();
        countyList.clear();
        //delete database that it is beeing created new
        //this.deleteDatabase(DataBaseHelper.DATABASE_NAME);

        dbHelper = new DataBaseHelper(this);

        //check if db exists
        //File database = getApplicationContext().getDatabasePath(DataBaseHelper.DATABASE_NAME);

        try {
            dbHelper.createDatabase();
        } catch (IOException e) {
            e.printStackTrace();
        }
/*
        if (!database.exists()) {
            dbHelper.getReadableDatabase();
            //copy
            if (dbHelper.copyDataBase()) {
                Toast.makeText(this, "Copy database success", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "Copy db error", Toast.LENGTH_SHORT).show();
                return;
            }
        }*/
        // get countylist
        countyList = dbHelper.getListCounties(/*selectedCantonAbb*/);

        // spinner
        spCounty = (Spinner) findViewById(R.id.spinner_county);

        // Create an ArrayAdapter using the database and a default spinner layout
        ArrayAdapter<County> adapter2 = new ArrayAdapter<County>(this, android.R.layout.simple_spinner_item, countyList);
        //specify the layout to use when the list of choices appears
        adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // apply the adapter to the spinner
        spCounty.setAdapter(adapter2);
    }

    public boolean saveUser(){
        String mailString = etMail1.getText().toString();
        String mail2String = etMail2.getText().toString();
        String pwdString = etPwd1.getText().toString();
        String pwd1String = etPwd2.getText().toString();

        if (!pwdString.equals(pwd1String) || pwdString.length() < 5) {
            etPwd1.setError(getString(R.string.error_invalid_password));
            etPwd1.requestFocus();
            etPwd1.setText("");
            etPwd2.setText("");
            return false;
        }
        if (!mailString.equals(mail2String) || !android.util.Patterns.EMAIL_ADDRESS.matcher(mailString).matches()) {
            etMail1.setError(getString(R.string.error_invalid_email));
            etMail1.requestFocus();
            return false;
        }

        if (pwdString.trim().isEmpty() ||mail2String.trim().isEmpty() || pwdString.trim().isEmpty() ||
                pwd1String.trim().isEmpty()){
            Toast.makeText(getApplicationContext(), "Please enter all informations",
                    Toast.LENGTH_LONG).show();
            etMail1.requestFocus();
            return false;
        }
        String cantonString = spCanton.getSelectedItem().toString();
        String countyString = spCounty.getSelectedItem().toString();

        User user = new User(mailString,pwdString, cantonString,countyString);
        vm = new ViewModelProvider(this).get(UserViewModel.class);
        vm.insert(user);
        return  true;
    }
}
