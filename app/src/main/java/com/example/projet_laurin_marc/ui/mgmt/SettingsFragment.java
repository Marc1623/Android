package com.example.projet_laurin_marc.ui.mgmt;

import android.os.Bundle;
import android.preference.PreferenceFragment;
import com.example.projet_laurin_marc.R;

public class SettingsFragment extends PreferenceFragment {

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		// Load the preferences from an XML resource
		addPreferencesFromResource(R.xml.preferences);
	}
}
