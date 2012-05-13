package pt.isel.pdm.yamba.TwitterAsync.services;

import java.util.Timer;
import java.util.TimerTask;

import pt.isel.pdm.yamba.YambaPreference;
import pt.isel.pdm.yamba.TwitterAsync.TwitterAsync;
import pt.isel.pdm.yamba.TwitterAsync.listeners.TimelineObtainedListener;
import winterwell.jtwitter.Twitter;
import android.app.Service;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.OnSharedPreferenceChangeListener;
import android.os.Handler;
import android.os.IBinder;
import android.preference.PreferenceManager;

public class GetTimelineService extends Service implements OnSharedPreferenceChangeListener {

	public final static String PARAM_TAG = "PARAM_TAG";
	
	private Iterable<Twitter.Status> _timeline;
	private int _refreshDelay; 	
	
	private SharedPreferences _preferences;
	
	@Override
	public void onCreate() {
		
		_preferences = PreferenceManager.getDefaultSharedPreferences(this);
		_preferences.registerOnSharedPreferenceChangeListener(this);
		
		_refreshDelay = Integer.parseInt(_preferences.getString(YambaPreference.TIMELINEREFRESHRATE_PREFERENCE, null));
		
		super.onCreate();
	}

	@Override
	public IBinder onBind(Intent intent) {
		// TODO Auto-generated method stub
		return null;
	}

	private final TwitterAsync _twitterAsync = TwitterAsync.connect();
		
	private Handler _handler = new Handler(); 
	
	private Timer _timer;
	
	private TimerTask _task = new TimerTask() {

		@Override
		public void run() {
			Twitter connection = _twitterAsync.getInnerConnection();
			
			_timeline = connection.getUserTimeline();
			
			_handler.post(new Thread() {
				
				@Override
				public void run() {
					
					TimelineObtainedListener listener = _twitterAsync.getTimelineObtainedListener();
					if(listener != null && _timeline != null) {
						listener.onTimelineObtained(_timeline);
					}
				}				
			});
		}			
	};
	
	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
				
		if(_timer == null) {
			_timer = new Timer();
		}
		else {
			_timer.cancel();
			_timer.purge();
		}
		
		_timer.scheduleAtFixedRate(_task, 0, _refreshDelay);
				
		if(_timeline != null) 
			_twitterAsync.getTimelineObtainedListener().onTimelineObtained(_timeline);
			
		return Service.START_REDELIVER_INTENT;
	}

	@Override
	public void onSharedPreferenceChanged(SharedPreferences arg0, String arg1) {
		
		if(arg1.equals(YambaPreference.TIMELINEREFRESHRATE_PREFERENCE)) {

			_refreshDelay = Integer.parseInt(_preferences.getString(YambaPreference.TIMELINEREFRESHRATE_PREFERENCE, null));
		
			_timer.cancel();
			_timer.scheduleAtFixedRate(_task, 0, _refreshDelay);
		}		
	}

}
