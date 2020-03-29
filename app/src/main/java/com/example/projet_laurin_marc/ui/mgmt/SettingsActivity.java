package com.example.projet_laurin_marc.ui.mgmt;

import android.app.Activity;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;


public class SettingsActivity extends AppCompatActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		getFragmentManager().beginTransaction()
				.replace(android.R.id.content,new SettingsFragment())
				.commit();
	}
}
