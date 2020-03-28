package com.example.projet_laurin_marc.ui;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.example.projet_laurin_marc.R;
import com.example.projet_laurin_marc.database.entity.User;
import com.example.projet_laurin_marc.database.viewModel.UserViewModel;
import com.example.projet_laurin_marc.ui.mgmt.LoginActivity;

import java.util.List;


public class ProfileFragment extends Fragment {

    private View view;
    private int userId;
    private User user;
    private List<User> users;
    private UserViewModel userViewModel;

    private EditText etMail;
    private EditText etPwd;
    private EditText etCanton;
    private EditText etCounty;

    private Button button_update;
    private Button button_delete;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_profil, container, false);
        // get userID from the person that logged in to the application
        userId = this.getArguments().getInt("userId");
        setData();
        update();
        delete();
        return view;
    }

    public void setData() {
        // get acces to database Users
        userViewModel = new ViewModelProvider(this).get(UserViewModel.class);
       // userViewModel.getUsers().
        userViewModel.getUsers().observe(getViewLifecycleOwner(), new Observer<List<User>>() {

            @Override //never called..
            public void onChanged(List<User> users) {


                int nr = userViewModel.getUsers().getValue().size();
                //Looping to check inputs
                for (int i = 0; i < nr; i++) {
                    if (userViewModel.getUsers().getValue().get(i).getId() == userId){
                        user = userViewModel.getUsers().getValue().get(i);

                        //get ref to textfield
                        etMail = view.findViewById(R.id.text_email);
                        etPwd = view.findViewById(R.id.text_pwd);
                        etCanton = view.findViewById(R.id.text_state);
                        etCounty = view.findViewById(R.id.text_county);

                        //set information into the textfields
                        etMail.setText(user.getEmail());
                        etPwd.setText(user.getPwd());
                        etCanton.setText(user.getCanton());
                        etCounty.setText(user.getCounty());
                    }
                }
            }
        });
    }

    public void update(){
        button_update = view.findViewById(R.id.button_update);
        button_update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //get ref to textfield
                etMail = view.findViewById(R.id.text_email);
                etPwd = view.findViewById(R.id.text_pwd);
                etCanton = view.findViewById(R.id.text_state);
                etCounty = view.findViewById(R.id.text_county);

                user.setEmail(etMail.getText().toString());
                user.setPwd(etPwd.getText().toString());
                user.setCanton(etCanton.getText().toString());
                user.setCounty(etCounty.getText().toString());

                userViewModel.update(user);
                Toast.makeText(getContext(), "User has been updated",
                        Toast.LENGTH_LONG).show();
            }
        });
    }

    // TODO: 21.03.2020 Delete Button
    public void delete(){
        button_delete = view.findViewById(R.id.button_delete);
        button_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //get ref to textfield
                etMail = view.findViewById(R.id.text_email);
                etPwd = view.findViewById(R.id.text_pwd);
                etCanton = view.findViewById(R.id.text_state);
                etCounty = view.findViewById(R.id.text_county);

                user.setEmail(etMail.getText().toString());
                user.setPwd(etPwd.getText().toString());
                user.setCanton(etCanton.getText().toString());
                user.setCounty(etCounty.getText().toString());

                userViewModel.delete(user);
                Toast.makeText(getContext(), "Your account has been deleted!",
                        Toast.LENGTH_LONG).show();

                //switch activity
                Intent intent = new Intent(getActivity(), LoginActivity.class);
                startActivity(intent);
                ((Activity) getActivity()).overridePendingTransition(0, 0); // no Animation in transition
            }
        });
    }

    public void onResume() {
        super.onResume();

        // Set title bar
        ((MainActivity) getActivity())
                .setActionBarTitle("Mein Profil");

    }

}
