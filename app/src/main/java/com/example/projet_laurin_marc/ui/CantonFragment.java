package com.example.projet_laurin_marc.ui;

import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import com.example.projet_laurin_marc.R;

public class CantonFragment extends Fragment {

    private View view;
    private String [] cantonsList;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_list, container, false);
        getChoice();
        return view;
    }

    public void getChoice(){
        // save string.xml arraylist in global var
        cantonsList = getResources().getStringArray(R.array.cantons_array);
        ListView listView = (ListView) view.findViewById(R.id.list);
        // create an adapter to list and set the list of stings
        ArrayAdapter<String> listViewAdapter = new ArrayAdapter<String>(
                getActivity(), android.R.layout.simple_list_item_1, cantonsList
        );
        //set adapdert to listView
        listView.setAdapter(listViewAdapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            //method is called when user clicks on an item in the list, position the important parameter that tells us which item was clicked.
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //get selected canton from list
                String canton = cantonsList[position];
                System.out.println(canton);

                // save selected canton in variable
                PreferenceManager.getDefaultSharedPreferences(getContext()).edit().putString("SELECTED_CANTON", canton).apply();

                //change to county fragment (countylist)
                Fragment fragment = new CountyFragment();
                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.fragment_container, fragment);
                fragmentTransaction.addToBackStack(null); // backstack for backbutton
                fragmentTransaction.commit();
            }
        });
    }

    public void onResume(){
        super.onResume();
        // Set title bar
        ((MainActivity) getActivity()).setActionBarTitle(getContext().getString(R.string.nav_title_cantons));
    }
}
