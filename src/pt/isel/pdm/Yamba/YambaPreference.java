package pt.isel.pdm.Yamba;

import android.os.Bundle;
import android.preference.EditTextPreference;
import android.preference.PreferenceActivity;
import android.text.InputType;

public class YambaPreference extends PreferenceActivity{
	
	@Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        addPreferencesFromResource(R.xml.preferences);
        
        EditTextPreference pref = (EditTextPreference)findPreference("maxTimelineEntries");
        pref.getEditText().setInputType(InputType.TYPE_CLASS_NUMBER);
        
        pref = (EditTextPreference)findPreference("maxCharStatus");
        pref.getEditText().setInputType(InputType.TYPE_CLASS_NUMBER);
    }
	
}
