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
import com.example.projet_laurin_marc.adapter.PersonAdapter;
import com.example.projet_laurin_marc.adapter.PersonListAdapter;
import com.example.projet_laurin_marc.database.entity.Person;
import com.example.projet_laurin_marc.database.viewModel.PersonViewModel;
import com.example.projet_laurin_marc.static_database.County;

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
            public void onChanged(List<Person> people) {
                personsList = new ArrayList<>();

                String selection = PreferenceManager.getDefaultSharedPreferences(getContext()).getString("SELECTED_COUNTY", "defaultStringNothingFound");
                for (int i = 0; i < people.size(); i++){
                    if(people.get(i).getCounty().equals(selection)){
                        Person p = people.get(i);
                        personsList.add(p);
                    }
                }
                PersonListAdapter adapter = new PersonListAdapter(getActivity(), personsList);
                listView.setAdapter(adapter);
            }
        });

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Person person = (Person) listView.getItemAtPosition(position);
                String personSelected = person.getAhv();

                // save residents id in variable -> pass it to next fragment
                PreferenceManager.getDefaultSharedPreferences(getContext()).edit().putString("SELECTED_PERSON_AHV", personSelected).apply();

                String userCounty = PreferenceManager.getDefaultSharedPreferences(getContext()).getString("User_County", "UserCounty_Default");;
                //check if the resident is in the same county as employe -> choose detailview with update and delete option
                if(person.getCounty().equals(userCounty)){
                    //change to list fragment (Resitents-list) -> Persons in County
                    Fragment fragment = new ResidentDetailsFragment();
                    FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                    FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                    fragmentTransaction.replace(R.id.fragment_container, fragment);
                    fragmentTransaction.addToBackStack(null);
                    fragmentTransaction.commit();
                }
                else{
                    //change to list fragment (Resitents-list) -> Persons in County
                    Fragment fragment = new ResidentDetails_NoAccessFragment();
                    FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                    FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                    fragmentTransaction.replace(R.id.fragment_container, fragment);
                    fragmentTransaction.addToBackStack(null);
                    fragmentTransaction.commit();
                }

            }
        });

        return view;
    }


    public void onResume(){
        super.onResume();

        // Set title bar
        ((MainActivity) getActivity())
                .setActionBarTitle(getContext().getString(R.string.residents));
    }
}
