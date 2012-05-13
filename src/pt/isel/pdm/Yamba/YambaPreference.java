package pt.isel.pdm.yamba;

import android.os.Bundle;
import android.preference.PreferenceActivity;

public class YambaPreference extends PreferenceActivity{
	
	public final static String USERNAME_PREFERENCE = "username";
	public final static String PASSWORD_PREFERENCE = "password";
	public final static String SERVICEURI_PREFERENCE = "serviceUri";
	public final static String STATUSMAXCHAR_PREFERENCE = "maxCharStatus";
	public final static String TIMELINEMAXENTRIES_PREFERENCE = "maxTimelineEntries";
	public final static String TIMELINEREFRESHRATE_PREFERENCE = "timelineRefresh";
	
	@Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        addPreferencesFromResource(R.xml.preferences);        
    }
	
}
