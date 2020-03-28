package com.example.projet_laurin_marc.ui;


import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;


import com.example.projet_laurin_marc.R;



public class SearchFragment extends Fragment {

    private EditText etAhv;
    private EditText etFirstname;
    private EditText etLastname;
    private EditText etCanton;
    private EditText etCounty;

    String ahvString;
    String firstString;
    String lastString;
    String cantonString;
    String countyString;

    private View view;
    private Button button_search;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_search, container, false);
        initializeForm();
        return view;
    }

    private void initializeForm() {
        etAhv = view.findViewById(R.id.text_ahv);
        etFirstname = view.findViewById(R.id.text_firstname);
        etLastname = view.findViewById(R.id.text_lastname);
        etCanton = view.findViewById(R.id.text_state);
        etCounty = view.findViewById(R.id.text_county);

        button_search = view.findViewById(R.id.button_search);
        button_search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ahvString = etAhv.getText().toString();
                firstString = etFirstname.getText().toString();
                lastString = etLastname.getText().toString();
                cantonString = etCanton.getText().toString();
                countyString = etCounty.getText().toString();

                //Send all inputs to the Search Result Fragment
                // Do the job of Search in SearchResultFragment
                // save selected canton in variable
               PreferenceManager.getDefaultSharedPreferences(getContext()).edit().putString("SELECTED_AHV", ahvString).apply();
                PreferenceManager.getDefaultSharedPreferences(getContext()).edit().putString("SELECTED_FIRST", firstString).apply();
                PreferenceManager.getDefaultSharedPreferences(getContext()).edit().putString("SELECTED_LAST", lastString).apply();
                PreferenceManager.getDefaultSharedPreferences(getContext()).edit().putString("SELECTED_CANTON", cantonString).apply();
                PreferenceManager.getDefaultSharedPreferences(getContext()).edit().putString("SELECTED_COUNTY", countyString).apply();

                //call new fragnemt to show result, access resultlist with getter from this Fragment
                //change to list fragment (Resitents-list) -> Persons in County
                Fragment fragment = new SearchResultFragment();
                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.fragment_container, fragment);
                fragmentTransaction.addToBackStack(null); // to be able to create a backbutton!!
                fragmentTransaction.commit();
            }
        });
    }


    public void onResume() {
        super.onResume();
        // Set title bar
        ((MainActivity) getActivity())
                .setActionBarTitle("Suchen");

    }
}
