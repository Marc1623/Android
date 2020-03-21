package com.example.projet_laurin_marc.ui;

import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.example.projet_laurin_marc.R;
import com.example.projet_laurin_marc.adapter.CountyListAdapter;
import com.example.projet_laurin_marc.adapter.PersonListAdapter;
import com.example.projet_laurin_marc.database.entity.Person;
import com.example.projet_laurin_marc.database.entity.User;
import com.example.projet_laurin_marc.database.viewModel.PersonViewModel;
import com.example.projet_laurin_marc.database.viewModel.UserViewModel;
import com.example.projet_laurin_marc.static_database.County;
import com.example.projet_laurin_marc.static_database.DataBaseHelper;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ResidentFragment extends Fragment {

    private View view;
    private List<Person> personsList;
    private PersonViewModel vmPers;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_list_ppl, container, false);

        final ListView listView = (ListView) view.findViewById(R.id.recycler_view);

        // get acces to database Users
        vmPers = new ViewModelProvider(this).get(PersonViewModel.class);

        // userViewModel.getUsers().
        vmPers.getPersons().observe(getViewLifecycleOwner(), new Observer<List<Person>>() {

            @Override
            public void onChanged(List<Person> persons) {
                personsList = new ArrayList<>();
                int nr = vmPers.getPersons().getValue().size();

                //get that value of selected county
                String selection = PreferenceManager.getDefaultSharedPreferences(getContext()).getString("SELECTED_COUNTY", "defaultStringIfNothingFound");

                //Looping trough persons db
                for (int i = 0; i < nr; i++) {
                    if (vmPers.getPersons().getValue().get(i).getCounty().equals(selection)) {
                        Person p = vmPers.getPersons().getValue().get(i);
                        personsList.add(p); //add each person to list with same county
                        System.out.println("AHV: " + p.getAhv());
                        System.out.println("First: " + p.getFirstname());
                        System.out.println("Last: " + p.getLastname());
                    }
                }

                PersonListAdapter adapter = new PersonListAdapter(getActivity(), personsList);
                listView.setAdapter(adapter);
            }
        });

        return view;
    }


    public void onResume(){
        super.onResume();

        // Set title bar
        ((MainActivity) getActivity())
                .setActionBarTitle("Residents");
    }
}