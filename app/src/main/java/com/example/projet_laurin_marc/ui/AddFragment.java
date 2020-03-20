package com.example.projet_laurin_marc.ui;


import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.projet_laurin_marc.R;
import com.example.projet_laurin_marc.database.entity.Person;
import com.example.projet_laurin_marc.database.viewModel.AddressViewModel;
import com.example.projet_laurin_marc.database.viewModel.PersonViewModel;

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

    private Button button_add;

    private PersonViewModel vmPers;
    private AddressViewModel vmAdr;

    //The system calls this when it's time for the fragment to draw its user interface for the first time
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_add, container, false);
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
                    Toast.makeText(getActivity(), "Person added to regitry", Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    public boolean savePerson() {
        String ahvString = etAhv.getText().toString();
        String firstString = etFirstname.getText().toString();
        String lastString = etLastnam.getText().toString();
        String streetString = etStreet.getText().toString();
        String zipString = etZip.getText().toString();
        String cityString = etCity.getText().toString();
        String phoneString = etPhone.getText().toString();
        String birthString = etBirthday.getText().toString();

        if (    ahvString.trim().isEmpty() || firstString.trim().isEmpty() || lastString.trim().isEmpty() ||
                streetString.trim().isEmpty() || zipString.trim().isEmpty() || cityString.trim().isEmpty() ||
                phoneString.trim().isEmpty() || birthString.trim().isEmpty()){
            Toast.makeText(getActivity().getApplicationContext(), "Please enter all informations",
                    Toast.LENGTH_LONG).show();
            // TODO focus on the first element without content
            etAhv.requestFocus();
            return false;
        }

        Person person = new Person(ahvString,firstString,lastString,phoneString, birthString, zipString, cityString, streetString);
        vmPers = new ViewModelProvider(this).get(PersonViewModel.class);
        vmPers.insert(person);
        return  true;
    }


    public void onResume() {
        super.onResume();

        // Set title bar
        ((MainActivity) getActivity())
                .setActionBarTitle("Hinzufügen");
    }

}