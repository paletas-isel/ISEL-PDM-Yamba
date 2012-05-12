package pt.isel.pdm.yamba;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

public class LauncherActivity extends Activity{

	@Override
	protected void onResume() {
		super.onResume();
		
		SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
		Intent intent;
		if(preferences.getString("username", null) == null) {			
			intent = new Intent(this, YambaPreference.class);
		}
		else {
			//intent = new Intent(this, TimelineActivity.class);
			intent = new Intent(this, TimelineActivity.class);
		}
		
		startActivity(intent);
	}
	
}
