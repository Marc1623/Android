package com.example.projet_laurin_marc.ui;

import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.example.projet_laurin_marc.R;
import com.example.projet_laurin_marc.database.entity.Person;
import com.example.projet_laurin_marc.database.viewModel.PersonViewModel;

import java.util.List;

// this class is called when the user (county employee) looks up other residents,
// that are not within the county he or she works for -> only display possible
public class ResidentDetails_NoAccessFragment extends Fragment {

    private View view;
    private Person person;
    private String ahv;

    private EditText etAHV;
    private EditText etFirstname;
    private EditText etLastname;
    private EditText etStreet;
    private EditText etZIP;
    private EditText etCity;
    private EditText etPhone;
    private EditText etBirthday;

    private PersonViewModel personViewModel;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_details_no_access, container, false);
        setData();
        return view;
    }

    public void setData() {
        // get acces to database PErson
        personViewModel = new ViewModelProvider(this).get(PersonViewModel.class);
        personViewModel.getPersons().observe(getViewLifecycleOwner(), new Observer<List<Person>>() {

            @Override
            public void onChanged(List<Person> people) {

                //get that value of selected person (AHV)
                ahv = PreferenceManager.getDefaultSharedPreferences(getContext()).getString("SELECTED_PERSON_AHV", "defaultStringIfNothingFound");

                //Looping to check inputs
                for (int i = 0; i < people.size(); i++) {
                    if (people.get(i).getAhv().equals(ahv)) {
                        person = people.get(i);

                        //get ref to textfield
                        etAHV = view.findViewById(R.id.text_ahv);
                        etFirstname = view.findViewById(R.id.text_firstname);
                        etLastname = view.findViewById(R.id.text_lastname);
                        etStreet = view.findViewById(R.id.text_street);
                        etZIP = view.findViewById(R.id.text_zip);
                        etCity = view.findViewById(R.id.text_city);
                        etPhone = view.findViewById(R.id.text_phone);
                        etBirthday = view.findViewById(R.id.text_birthday);

                        //set information into the textfields
                        etAHV.setText(person.getAhv());
                        etFirstname.setText(person.getFirstname());
                        etLastname.setText(person.getLastname());
                        etStreet.setText(person.getStreet());
                        etZIP.setText(person.getZip());
                        etCity.setText(person.getCity());
                        etPhone.setText(person.getPhone());
                        etBirthday.setText(person.getBirthday());
                    }
                }
            }
        });
    }

    public void onResume() {
        super.onResume();
        // Set title bar
        ((MainActivity) getActivity()).setActionBarTitle(getContext().getString(R.string.chapter_resitentDetails));
    }
}