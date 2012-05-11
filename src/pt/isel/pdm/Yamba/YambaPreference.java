package pt.isel.pdm.yamba;

import android.os.Bundle;
import android.preference.PreferenceActivity;

public class YambaPreference extends PreferenceActivity{
	
	@Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        addPreferencesFromResource(R.xml.preferences);        
    }
	
}
