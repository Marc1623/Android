package com.example.projet_laurin_marc.ui;


import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
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
    private String userId;
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
        userId = this.getArguments().getString("userId");
        setData();
        update();
        delete();
        return view;
    }

    //set data from logged user
    public void setData() {
        // get acces to database Users
        userViewModel = new ViewModelProvider(this).get(UserViewModel.class);
       // userViewModel.getUsers().
        userViewModel.getUsers().observe(getViewLifecycleOwner(), new Observer<List<User>>() {

            @Override //never called..
            public void onChanged(List<User> users) {

                if(users == null){
                    return;
                }

                int nr = userViewModel.getUsers().getValue().size();
                //Looping to check inputs
                for (int i = 0; i < nr; i++) {
                    if (userViewModel.getUsers().getValue().get(i).getId().equals(userId)){
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

    // action when you make changes and press update button
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
                Toast.makeText(getContext(), R.string.msg_user_updated,
                        Toast.LENGTH_LONG).show();
            }
        });
    }

    //action when you press delete button
    public void delete(){
        button_delete = view.findViewById(R.id.button_delete);
        button_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        switch (which){
                            case DialogInterface.BUTTON_POSITIVE:
                                //Yes button clicked
                                userViewModel.delete(user);// delete user from db

                                Toast.makeText(getContext(), R.string.msg_account_deleted,
                                        Toast.LENGTH_LONG).show();

                                //switch to login activity
                                Intent intent = new Intent(getActivity(), LoginActivity.class);
                                startActivity(intent);
                                ((Activity) getActivity()).overridePendingTransition(0, 0); // no Animation in transition
                                break;

                            case DialogInterface.BUTTON_NEGATIVE:
                                //No button clicked, do nothing
                                break;
                        }
                    }
                };

                // to be sure user is not being deleted accidentally, ask confirmation of user
                AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                builder.setMessage(getContext().getString(R.string.alert_delete)).setPositiveButton(getContext().getString(R.string.yes), dialogClickListener)
                        .setNegativeButton(getContext().getString(R.string.no), dialogClickListener).show();

            }
        });
    }

    public void onResume() {
        super.onResume();

        // Set title bar
        ((MainActivity) getActivity())
                .setActionBarTitle(getContext().getString(R.string.nav_title_profile));

    }

}
