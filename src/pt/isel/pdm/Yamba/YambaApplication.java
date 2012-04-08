package pt.isel.pdm.Yamba;

import android.app.Application;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

public class YambaApplication extends Application{

	@Override
	public void onCreate() {	

		SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
		
		if(preferences.getString("username", null) == null) {
			
			Intent intent = new Intent(this, YambaPreference.class);
			intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
			startActivity(intent);
			
		}
		
	}
	
}
