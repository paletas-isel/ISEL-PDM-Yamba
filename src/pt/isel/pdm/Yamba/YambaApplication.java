package pt.isel.pdm.Yamba;

import pt.isel.pdm.Yamba.TwitterAsync.TwitterAsync;
import pt.isel.pdm.Yamba.TwitterAsync.listeners.TwitterExceptionListener;
import pt.isel.pdm.Yamba.exceptions.BadCredentialsException;
import pt.isel.pdm.Yamba.exceptions.InvalidAPIException;
import pt.isel.pdm.Yamba.exceptions.TwitterException;
import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.OnSharedPreferenceChangeListener;
import android.preference.PreferenceManager;
import android.widget.Toast;

public class YambaApplication extends Application{

	private static final String _ServiceUriKey = "serviceUri";
	private static final String _PasswordKey = "password";
	private static final String _UsernameKey = "username";
	
	public static final String TweetExtra = "TWEET_EXTRA";
	
	private OnSharedPreferenceChangeListener _preferenceListener;
	
	@Override
	public void onCreate() {	

		SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
			
		_preferenceListener = new OnSharedPreferenceChangeListener() {
			
			@Override
			public void onSharedPreferenceChanged(SharedPreferences sharedPreferences,
					String key) {

				if(key.equalsIgnoreCase(_ServiceUriKey)) {
					
					TwitterAsync.setServiceUri(sharedPreferences.getString(key, null));
					
				} else if(key.equalsIgnoreCase(_UsernameKey)) {
					
					TwitterAsync.setUsername(sharedPreferences.getString(key, null));
					
				} else if(key.equalsIgnoreCase(_PasswordKey)) {
					
					TwitterAsync.setPassword(sharedPreferences.getString(key, null));
					
				}
				
			}
		};
		
		preferences.registerOnSharedPreferenceChangeListener(_preferenceListener);
		
		TwitterAsync.setServiceUri(PreferenceManager.getDefaultSharedPreferences(this).getString(_ServiceUriKey, null));
		TwitterAsync.setUsername(PreferenceManager.getDefaultSharedPreferences(this).getString(_UsernameKey, null));
		TwitterAsync.setPassword(PreferenceManager.getDefaultSharedPreferences(this).getString(_PasswordKey, null));
		
		final Context context = this;
		TwitterAsync.setTwitterExceptionListener(new TwitterExceptionListener() {
			
			@Override
			public void onExceptionThrown(TwitterException e) {
				
				boolean showSettings = false;
				
				if(e instanceof BadCredentialsException) {
					showSettings = true;
					Toast.makeText(context, R.string.badCredentials, Toast.LENGTH_LONG);
				} else if(e instanceof InvalidAPIException) {
					showSettings = true;
					Toast.makeText(context, R.string.invalidApi, Toast.LENGTH_LONG);
				}
				else {
					Toast.makeText(context, R.string.errorMessage, Toast.LENGTH_SHORT);					
				}
				
				if(showSettings) {
					Intent intent = new Intent(context, YambaPreference.class);			
					intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
					startActivity(intent);
				}
			}
		});
	}
	
}
