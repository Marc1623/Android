package com.example.projet_laurin_marc.ui;

import android.app.DatePickerDialog;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.example.projet_laurin_marc.R;
import com.example.projet_laurin_marc.database.entity.Person;
import com.example.projet_laurin_marc.database.entity.User;

import com.example.projet_laurin_marc.database.viewModel.PersonViewModel;
import com.example.projet_laurin_marc.database.viewModel.UserViewModel;

import java.util.Calendar;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class AddFragment extends Fragment {

    private EditText etAhv;
    private EditText etFirstname;
    private EditText etLastnam;
    private EditText etStreet;
    private EditText etZip;
    private EditText etCity;
    private EditText etPhone;
    private EditText etBirthday;
    private View view;

    private int userId;
    private User user;
    private String canton;
    private String county = "Test";
    private UserViewModel userViewModel;

    private Button button_add;

    private PersonViewModel vmPers;
    private DatePickerDialog.OnDateSetListener mDateListener;

    //The system calls this when it's time for the fragment to draw its user interface for the first time
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_add, container, false);
        userId = this.getArguments().getInt("userId1");
        //get county and canton from the user (county employee)
        getUser();
        initializeForm();
        return view;
    }

    // The system calls this when creating the fragment. Within your implementation,
    // you should initialize essential components of the fragment that you want to
    // retain when the fragment is paused or stopped, then resumed.
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    private void initializeForm() {
        etAhv = view.findViewById(R.id.text_ahv);
        etFirstname = view.findViewById(R.id.text_firstname);
        etLastnam = view.findViewById(R.id.text_lastname);
        etStreet = view.findViewById(R.id.text_street);
        etZip = view.findViewById(R.id.text_zip);
        etCity = view.findViewById(R.id.text_city);
        etPhone = view.findViewById(R.id.text_phone);

        etBirthday = view.findViewById(R.id.text_birthday);
        datepicker();

        button_add = view.findViewById(R.id.button_add);
        button_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (savePerson()) {
                    //reset EditTextfields..
                    etAhv.setText("");
                    etFirstname.setText("");
                    etLastnam.setText("");
                    etStreet.setText("");
                    etZip.setText("");
                    etCity.setText("");
                    etPhone.setText("");
                    etBirthday.setText("");

                    // give msg (Pop-Up), that login was successful
                    Toast.makeText(getActivity(), R.string.msg_person_added_regitry, Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    public boolean savePerson() {
        Boolean valid = true;
        // get strings out of the textfields, and save in local varialbes
        String ahvString = etAhv.getText().toString();
        String firstString = etFirstname.getText().toString();
        String lastString = etLastnam.getText().toString();
        String streetString = etStreet.getText().toString();
        String zipString = etZip.getText().toString();
        String cityString = etCity.getText().toString();
        String phoneString = etPhone.getText().toString();
        String birthString = etBirthday.getText().toString();

        // check if all entry fields are filled
        if (ahvString.trim().isEmpty() || firstString.trim().isEmpty() || lastString.trim().isEmpty() ||
                streetString.trim().isEmpty() || zipString.trim().isEmpty() || cityString.trim().isEmpty() ||
                phoneString.trim().isEmpty() || birthString.trim().isEmpty()) {
            Toast.makeText(getActivity().getApplicationContext(), R.string.error_enter_all_information,
                    Toast.LENGTH_LONG).show();
            etAhv.requestFocus();
            valid = false;
        }

        //checks on phone Format and Zip etc..
        if (!phoneNumberValidation(phoneString)) {
            etPhone.setError(getString(R.string.error_fehler));
            valid = false;
        }
        if(!zipValidation(zipString)){
            etZip.setError(getString(R.string.error_fehler));
            valid = false;
        }
        if(valid){
            // if entryFields are not empty save person in DB,
            Person person = new Person(ahvString, firstString, lastString, phoneString, birthString, zipString, cityString, streetString, canton, county);
            vmPers = new ViewModelProvider(this).get(PersonViewModel.class);
            vmPers.insert(person);
        }
        return valid;
    }

    public void datepicker() {
        etBirthday.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar cal = Calendar.getInstance();
                int year = cal.get(Calendar.YEAR);
                int month = cal.get(Calendar.MONTH);
                int day = cal.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog dialog = new DatePickerDialog(
                        getContext(),
                        android.R.style.Theme_Material_Light_Dialog_MinWidth,
                        mDateListener,
                        year, month, day);
                dialog.show();
            }
        });

        mDateListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                String date = dayOfMonth + "." + month + "." + year;
                etBirthday.setText(date);

            }
        };

    }

    public Boolean phoneNumberValidation(String number) {
        Boolean valid = false;
        String pattern = "^\\s*(?:\\+?(\\d{1,3}))?[-. (]*(\\d{3})[-. )]*(\\d{3})[-. ]*(\\d{4})(?: *x(\\d+))?\\s*$";
        Matcher m = null;

        Pattern r = Pattern.compile(pattern);

        if (!number.isEmpty()) {
            m = r.matcher(number.trim());
        }
        if (m.find()) {
            valid = true;
        } else {
            valid = false;
        }
        return valid;
    }

    public Boolean zipValidation(String zip){
        Boolean valid = false;
        String pattern = "^[1-9][0-9][0-9][0-9]$";
        Matcher m = null;

        Pattern r = Pattern.compile(pattern);

        if (!zip.isEmpty()) {
            m = r.matcher(zip.trim());
        }
        if (m.find()) {
            valid = true;
        } else {
            valid = false;
        }
        return valid;
    }

    //get the user
    public void getUser() {
        // get acces to database Users
        userViewModel = new ViewModelProvider(this).get(UserViewModel.class);
        // userViewModel.getUsers().
        userViewModel.getUsers().observe(getViewLifecycleOwner(), new Observer<List<User>>() {

            @Override
            public void onChanged(List<User> users) {
                int nr = userViewModel.getUsers().getValue().size();
                //Looping to check inputs
                for (int i = 0; i < nr; i++) {
                    if (userViewModel.getUsers().getValue().get(i).getId() == userId) {
                        user = userViewModel.getUsers().getValue().get(i);
                        canton = user.getCanton();
                        county = user.getCounty();
                    }
                }
            }
        });
    }

    public void onResume() {
        super.onResume();
        // Set title bar
        ((MainActivity) getActivity()).setActionBarTitle(getContext().getString(R.string.nav_title_new));
    }

}
