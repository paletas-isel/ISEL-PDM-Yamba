package pt.isel.pdm.Yamba;

import pt.isel.pdm.Yamba.TwitterAsync.TwitterAsync;
import android.app.Application;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.OnSharedPreferenceChangeListener;
import android.preference.PreferenceManager;

public class YambaApplication extends Application{

	private static final String _ServiceUriKey = "serviceUri";
	
	@Override
	public void onCreate() {	

		SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
		
		if(preferences.getString("username", null) == null) {
			
			Intent intent = new Intent(this, YambaPreference.class);
			intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
			startActivity(intent);
			
		}
	
		preferences.registerOnSharedPreferenceChangeListener(new OnSharedPreferenceChangeListener() {
			
			@Override
			public void onSharedPreferenceChanged(SharedPreferences sharedPreferences,
					String key) {

				if(key.equalsIgnoreCase(_ServiceUriKey)) {
					
					TwitterAsync.setServiceUri(sharedPreferences.getString(key, null));
					
				}
				
			}
		});
		
		TwitterAsync.setServiceUri(PreferenceManager.getDefaultSharedPreferences(this).getString(_ServiceUriKey, null));
	}
	
}
