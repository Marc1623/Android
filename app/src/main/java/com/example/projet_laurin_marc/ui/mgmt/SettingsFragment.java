package com.example.projet_laurin_marc.ui.mgmt;

import android.os.Bundle;
import android.preference.PreferenceFragment;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.projet_laurin_marc.R;
import com.example.projet_laurin_marc.ui.CantonFragment;
import com.example.projet_laurin_marc.ui.SearchResultFragment;

public class SettingsFragment extends PreferenceFragment {

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		// Load the preferences from an XML resource
		addPreferencesFromResource(R.xml.preferences);

	}
}
