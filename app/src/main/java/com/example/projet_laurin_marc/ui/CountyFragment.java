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

import com.example.projet_laurin_marc.R;
import com.example.projet_laurin_marc.adapter.CountyListAdapter;
import com.example.projet_laurin_marc.static_database.County1;
import com.example.projet_laurin_marc.static_database.DataBaseHelper;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class CountyFragment extends Fragment {

    private View view;
    private List<County1> countyObjectList;
    private DataBaseHelper dbHelper;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_list, container, false);
        getChoice();
        return view;
    }

    public void getChoice() {
        countyListByCanton(); // get list of counties
        final ListView listView = view.findViewById(R.id.list);
        listView.setAdapter(new CountyListAdapter(getActivity(), countyObjectList));
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                County1 county = (County1) listView.getItemAtPosition(position);
                String countySelected = county.getCounty();
                // save selected canton in variable
                PreferenceManager.getDefaultSharedPreferences(getContext()).edit().putString("SELECTED_COUNTY", countySelected).apply();

                //change to list fragment (Resitents-list) -> Persons in County
                Fragment fragment = new ResidentFragment();
                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.fragment_container, fragment);
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();
            }
        });
    }

    public void countyListByCanton() {
        // if canton was choosen then get the list of counties in the Spinner
        countyObjectList = new ArrayList<>();
        countyObjectList.clear();
        dbHelper = new DataBaseHelper(getActivity());

        try {
            dbHelper.createDatabase();
        } catch (IOException e) {
            e.printStackTrace();
        }

        //get that value of selected canton
        String selection = PreferenceManager.getDefaultSharedPreferences(getContext()).getString("SELECTED_CANTON", "defaultStringIfNothingFound");

       /// get countylist
        countyObjectList = dbHelper.getCountiesByCanton(selection);
    }

    public void onResume(){
        super.onResume();
        // Set title bar
        ((MainActivity) getActivity()).setActionBarTitle(getContext().getString(R.string.county));
    }
}
