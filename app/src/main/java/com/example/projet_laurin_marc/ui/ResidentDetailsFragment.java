package com.example.projet_laurin_marc.ui;

import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.example.projet_laurin_marc.R;
import com.example.projet_laurin_marc.database.entity.Person;
import com.example.projet_laurin_marc.database.entity.User;
import com.example.projet_laurin_marc.database.viewModel.PersonViewModel;
import com.example.projet_laurin_marc.database.viewModel.UserViewModel;

import java.util.List;

public class ResidentDetailsFragment extends Fragment {

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

    private Button button_update;
    private Button button_delete;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_details, container, false);
        setData();
        update();
        delete();
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


                int nr = personViewModel.getPersons().getValue().size();
                //Looping to check inputs
                for (int i = 0; i < nr; i++) {
                    if (personViewModel.getPersons().getValue().get(i).getAhv().equals(ahv)) {
                        person = personViewModel.getPersons().getValue().get(i);

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

    public void update() {
        button_update = view.findViewById(R.id.button_update_person);
        button_update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //set information into the textfields
                person.setAhv(etAHV.getText().toString());
                person.setFirstname(etFirstname.getText().toString());
                person.setLastname(etLastname.getText().toString());
                person.setStreet(etStreet.getText().toString());
                person.setZip(etZIP.getText().toString());
                person.setCity(etCity.getText().toString());
                person.setPhone(etPhone.getText().toString());
                person.setBirthday(etBirthday.getText().toString());

                personViewModel.update(person);
                Toast.makeText(getContext(), R.string.msg_changes_saved,
                        Toast.LENGTH_LONG).show();
            }
        });
    }

    public void delete() {
        button_delete = view.findViewById(R.id.button_delete_person);
        button_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                personViewModel.delete(person);
                Toast.makeText(getContext(), "person has been deleted",
                        Toast.LENGTH_LONG).show();
                //go to the Canton Fragment
                Fragment fragment = new CantonFragment();
                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.fragment_container, fragment);
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();
            }
        });

    }

    public void onResume() {
        super.onResume();

        // Set title bar
        ((MainActivity) getActivity())
                .setActionBarTitle(getContext().getString(R.string.chapter_resitentDetails));

    }
}