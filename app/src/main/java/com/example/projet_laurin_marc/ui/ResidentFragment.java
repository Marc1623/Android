package com.example.projet_laurin_marc.ui;

import androidx.fragment.app.Fragment;

public class ResidentFragment extends Fragment {


    //get a list of resitends by county



    public void onResume(){
        super.onResume();

        // Set title bar
        ((MainActivity) getActivity())
                .setActionBarTitle("Residents");
    }
}
