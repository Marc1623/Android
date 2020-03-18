package com.example.projet_laurin_marc;


import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class AddFragment extends Fragment {

    private EditText etAhv;
    private EditText etFirstname;
    private EditText etLastnam;
    private EditText etStreet;
    private EditText etZip;
    private EditText etCity;
    private EditText etPhone;
    private EditText etBirthday;
    private View view;

    private Button button_add;

    //The system calls this when it's time for the fragment to draw its user interface for the first time
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_add, container, false);
        //initializeForm();

        return view;
    }

    // The system calls this when creating the fragment. Within your implementation,
    // you should initialize essential components of the fragment that you want to
    // retain when the fragment is paused or stopped, then resumed.
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


    }
/*
    private void initializeForm() {
        etAhv = view.findViewById(R.id.text_ahv) ;
       etFirstname = view.findViewById(R.id.text_firstname);
        etLastnam = view.findViewById(R.id.text_lastname) ;
        etStreet = view.findViewById(R.id.text_street) ;
        etZip = view.findViewById(R.id.text_zip) ;
        etCity = view.findViewById(R.id.text_city) ;
        etPhone = view.findViewById(R.id.text_phone) ;
        etBirthday = view.findViewById(R.id.text_birthday) ;

        button_add = view.findViewById(R.id.button_add);
        button_add.setOnClickListener(view -> saveChanges(
                etAhv.getText().toString(),
                etLastnam.getText().toString(),
                etStreet.getText().toString(),
                etZip.getText().toString(),
                etCity.getText().toString(),
                etPhone.getText().toString(),
                etBirthday.getText().toString()
        ));
    }

    private void saveChanges(String firstName, String lastName, String email, String pwd, String pwd2) {
        if (!pwd.equals(pwd2) || pwd.length() < 5) {
            etPwd1.setError(getString(R.string.error_invalid_password));
            etPwd1.requestFocus();
            etPwd1.setText("");
            etPwd2.setText("");
            return;
        }
        if (!android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            etEmail.setError(getString(R.string.error_invalid_email));
            etEmail.requestFocus();
            return;
        }
        ClientEntity newClient = new ClientEntity(email, firstName, lastName, pwd);

        new CreateClient(getApplication(), new OnAsyncEventListener() {
            @Override
            public void onSuccess() {
                Log.d(TAG, "createUserWithEmail: success");
                setResponse(true);
            }

            @Override
            public void onFailure(Exception e) {
                Log.d(TAG, "createUserWithEmail: failure", e);
                setResponse(false);
            }
        }).execute(newClient);
*/

    public void onResume(){
        super.onResume();

        // Set title bar
        ((MainActivity) getActivity())
                .setActionBarTitle("Hinzufügen");
    }

}
