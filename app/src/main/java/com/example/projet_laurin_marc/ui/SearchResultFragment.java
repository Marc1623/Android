package com.example.projet_laurin_marc.ui;

import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
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

    private String ahvString;
    private String firstString;
    private String lastString;
    private String cantonString;
    private String countyString;

    private List<Person> pplFirst;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_list_ppl,container,false);
        search();
        return view;
    }

    private void search(){
        final ListView listView = view.findViewById(R.id.recycler_view);

        personViewModel = new ViewModelProvider(this).get(PersonViewModel.class);
        personViewModel.getPersons().observe(getViewLifecycleOwner(), new Observer<List<Person>>() {
            @Override
            public void onChanged(List<Person> people) {
                pplFirst = new ArrayList<>();
                //get that value of serachfields
                ahvString = PreferenceManager.getDefaultSharedPreferences(getContext()).getString("SELECTED_AHV", "AHV_Default");
                firstString = PreferenceManager.getDefaultSharedPreferences(getContext()).getString("SELECTED_FIRST", "FIRST_Default");
                lastString = PreferenceManager.getDefaultSharedPreferences(getContext()).getString("SELECTED_LAST", "LAST_Default");
                cantonString = PreferenceManager.getDefaultSharedPreferences(getContext()).getString("SELECTED_CANTON", "CANTON_Default");
                countyString = PreferenceManager.getDefaultSharedPreferences(getContext()).getString("SELECTED_COUNTY", "COUNTY_Default");

                //Looping to check inputs
                for (int i = 0; i < people.size(); i++) {
                    if(people.get(i).getAhv().equals(ahvString) && ahvString != null){
                        pplFirst.add(people.get(i));
                    }
                    if (people.get(i).getFirstname().equals(firstString) && firstString != null) {
                        //multiple ppl possible
                        System.out.println(people.get(i).getLastname());
                        Person p = personViewModel.getPersons().getValue().get(i);
                        pplFirst.add(p);
                    }
                    if(people.get(i).getLastname().equals(lastString) && lastString != null){
                        pplFirst.add(people.get(i));
                    }
                    if(people.get(i).getCanton().equals(cantonString) && cantonString != null
                            && !(people.get(i).getAhv().equals(ahvString) && ahvString != null)
                            && !(people.get(i).getFirstname().equals(firstString) && firstString != null)
                            && !(people.get(i).getLastname().equals(lastString) && lastString != null))
                    {
                        pplFirst.add(people.get(i));
                    }
                    if(people.get(i).getCounty().equals(countyString) && countyString != null
                            && !(people.get(i).getCanton().equals(cantonString) && cantonString != null)
                            && !(people.get(i).getAhv().equals(ahvString) && ahvString != null)
                            && !(people.get(i).getFirstname().equals(firstString) && firstString != null)
                            && !(people.get(i).getLastname().equals(lastString) && lastString != null))
                    {
                        pplFirst.add(people.get(i));
                    }
                }
                PersonListAdapter adapter = new PersonListAdapter(getActivity(),pplFirst);
                listView.setAdapter(adapter);
            }
        });

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Person person = (Person) listView.getItemAtPosition(position);
                String personSelected = person.getAhv();

                //save selected ahv number
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
    }

}
