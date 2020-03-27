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
import com.example.projet_laurin_marc.adapter.PersonListAdapter;
import com.example.projet_laurin_marc.database.entity.Person;
import com.example.projet_laurin_marc.database.viewModel.PersonViewModel;

import java.util.ArrayList;
import java.util.List;

public class SearchResultFragment extends Fragment {

    private View view;
    private PersonViewModel personViewModel;


    String ahvString;
    String firstString;
    String lastString;
    String cantonString;
    String countyString;

    private Person person; //solution AVS
    private List<Person> pplFirst;  //solution Fristname
    private List<Person> pplLast;  //solution Lastname
    private List<Person> pplCanton;
    private List<Person> pplCounty;

    private List<Person> pplResult;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_list_ppl, container, false);

        final ListView listView = (ListView) view.findViewById(R.id.recycler_view);

        // get acces to database Persons
        personViewModel = new ViewModelProvider(this).get(PersonViewModel.class);
        // remove observer first
        personViewModel.getPersons().removeObservers(this);

        //get that value of serachfields
        ahvString = PreferenceManager.getDefaultSharedPreferences(getContext()).getString("SELECTED_AHV", "AHV_Default");
        firstString = PreferenceManager.getDefaultSharedPreferences(getContext()).getString("SELECTED_FIRST", "FIRST_Default");
        lastString = PreferenceManager.getDefaultSharedPreferences(getContext()).getString("SELECTED_LAST", "LAST_Default");
        cantonString = PreferenceManager.getDefaultSharedPreferences(getContext()).getString("SELECTED_CANTON", "CANTON_Default");
        countyString = PreferenceManager.getDefaultSharedPreferences(getContext()).getString("SELECTED_COUNTY", "COUNTY_Default");

        System.out.println("Firstname: " +firstString);

        // find all ppl that meet search criteria
        //getPersonByAVS();
        getPersonByFirst();
        //getPersonByLast();
        //getPersonByCanton();
        //getPersonByCounty();

        //merge lists and sort Resultlist alphabetically
      /*  pplResult.add(person);
        pplResult.addAll(pplFirst);
        pplResult.addAll(pplLast);
        pplResult.addAll(pplCanton);
        pplResult.addAll(pplCounty);*/

        // show list
        PersonListAdapter adapter = new PersonListAdapter(getActivity(), pplFirst);
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Person person = (Person) listView.getItemAtPosition(position);
                String personSelected = person.getAhv();

                // save selected canton in variable
                PreferenceManager.getDefaultSharedPreferences(getContext()).edit().putString("SELECTED_PERSON_AHV", personSelected).apply();

                //change to list fragment (Resitents-list) -> Persons in County
                Fragment fragment = new ResidentDetailsFragment();
                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.fragment_container, fragment);
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();
            }
        });

        return view;
    }

    /*
    public void getPersonByAVS() {
        // get acces to database Persons
        personViewModel = new ViewModelProvider(this).get(PersonViewModel.class);
        // get all persons
        personViewModel.getPersons().observe(getViewLifecycleOwner(), new Observer<List<Person>>() {

            @Override
            public void onChanged(List<Person> persons) {
                //Looping to check inputs
                for (int i = 0; i < persons.size(); i++) {
                    if (persons.get(i).getAhv().equals(ahvString) && ahvString != null) {
                        person = persons.get(i);
                    }
                }
            }
        });
    }
    */

    public void getPersonByFirst() {

        // get all persons
        personViewModel.getPersons().observe(getViewLifecycleOwner(), new Observer<List<Person>>() {

            // problem, onChanged method is not being triggered??
            @Override
            public void onChanged(List<Person> persons) {
                pplFirst = new ArrayList<>();
                //Looping to check inputs
                for (int i = 0; i < personViewModel.getPersons().getValue().size(); i++) {
                    if (personViewModel.getPersons().getValue().get(i).getFirstname().equals(firstString) && firstString != null) {
                        //multiple ppl possible
                        pplFirst.add(persons.get(i));
                    }
                }
            }
        });
    }
/*
    public void getPersonByLast() {
        // get acces to database Persons
        personViewModel = new ViewModelProvider(this).get(PersonViewModel.class);
        // get all persons
        personViewModel.getPersons().observe(getViewLifecycleOwner(), new Observer<List<Person>>() {

            @Override
            public void onChanged(List<Person> persons) {
                pplLast = new ArrayList<>();
                //Looping to check inputs
                for (int i = 0; i < persons.size(); i++) {
                    if (persons.get(i).getLastname().equals(lastString) && lastString != null) {
                        //multiple ppl possible
                        pplLast.add(persons.get(i));
                    }
                }
            }
        });
    }

    public void getPersonByCanton() {
        // get acces to database Persons
        personViewModel = new ViewModelProvider(this).get(PersonViewModel.class);
        // get all persons
        personViewModel.getPersons().observe(getViewLifecycleOwner(), new Observer<List<Person>>() {

            @Override
            public void onChanged(List<Person> persons) {
                pplCanton = new ArrayList<>();
                //Looping to check inputs
                for (int i = 0; i < persons.size(); i++) {
                    if (persons.get(i).getCanton().equals(cantonString) && cantonString != null) {
                        //multiple ppl possible
                        pplCanton.add(persons.get(i));
                    }
                }
            }
        });
    }

    public void getPersonByCounty() {
        // get acces to database Persons
        personViewModel = new ViewModelProvider(this).get(PersonViewModel.class);
        // get all persons
        personViewModel.getPersons().observe(getViewLifecycleOwner(), new Observer<List<Person>>() {

            @Override
            public void onChanged(List<Person> persons) {
                pplCounty = new ArrayList<>();
                //Looping to check inputs
                for (int i = 0; i < persons.size(); i++) {
                    if (persons.get(i).getCounty().equals(countyString) && countyString != null) {
                        //multiple ppl possible
                        pplCounty.add(persons.get(i));
                    }
                }
            }
        });
    }
*/
    public void onResume() {
        super.onResume();

        // Set title bar
        ((MainActivity) getActivity())
                .setActionBarTitle("Results");
    }
}
