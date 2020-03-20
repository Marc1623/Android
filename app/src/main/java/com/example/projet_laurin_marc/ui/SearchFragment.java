package com.example.projet_laurin_marc.ui;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

import com.example.projet_laurin_marc.R;


public class SearchFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_search, container, false);


    }

    public void onResume(){
        super.onResume();

        // Set title bar
        ((MainActivity) getActivity())
                .setActionBarTitle("Suchen");

    }


}