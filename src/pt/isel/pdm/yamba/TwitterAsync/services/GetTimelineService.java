package pt.isel.pdm.yamba.TwitterAsync.services;

import java.sql.SQLException;
import java.util.Collections;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import pt.isel.pdm.java.lang.LazyLoadIterableAdapter;
import pt.isel.pdm.yamba.YambaPreference;
import pt.isel.pdm.yamba.TwitterAsync.TwitterAsync;
import pt.isel.pdm.yamba.TwitterAsync.listeners.TimelineObtainedListener;
import pt.isel.pdm.yamba.ViewModel.StatusToTweetAdapter;
import pt.isel.pdm.yamba.ViewModel.Tweet;
import pt.isel.pdm.yamba.ViewModel.TweetsSqliteDataSource;
import winterwell.jtwitter.Twitter;
import winterwell.jtwitter.Twitter.Status;
import android.app.Service;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.OnSharedPreferenceChangeListener;
import android.os.Handler;
import android.os.IBinder;
import android.preference.PreferenceManager;

public class GetTimelineService extends Service implements OnSharedPreferenceChangeListener {

	public final static String PARAM_TAG = "PARAM_TAG";
	
	private Iterable<Tweet> _timeline;
	private int _refreshDelay, _savedEntriesCount;
	private boolean _enableAutoRefresh;
	
	private long _lastSchedule;
	
	private SharedPreferences _preferences;
	
	private TweetsSqliteDataSource _source;
	
	@Override
	public void onCreate() {
		super.onCreate();

		_preferences = PreferenceManager.getDefaultSharedPreferences(this);
		_preferences.registerOnSharedPreferenceChangeListener(this);
		
		_refreshDelay = Integer.parseInt(_preferences.getString(YambaPreference.TIMELINEREFRESHRATE_PREFERENCE, null));
		_enableAutoRefresh = _preferences.getBoolean(YambaPreference.TIMELINEAUTOREFRESH_PREFERENCE, false);
		_savedEntriesCount = Integer.parseInt(_preferences.getString(YambaPreference.TIMELINEMAXENTRIES_PREFERENCE, null));
		
		_source = new TweetsSqliteDataSource(this);
	}

	
	@Override
	public IBinder onBind(Intent intent) {
		// TODO Auto-generated method stub
		return null;
	}

	private final TwitterAsync _twitterAsync = TwitterAsync.connect();
		
	private Handler _handler = new Handler(); 
	
	private Timer _timer;
	
	
	
	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
				
		if(_timer == null) {
			_timer = new Timer();
		}
		else {
			_timer.cancel();
			_timer.purge();
			_timer = new Timer();
		}
		
		if(_enableAutoRefresh)
			scheduleLooped();
		else
			scheduleOnce();
				
		if(_timeline != null) 
			_twitterAsync.getTimelineObtainedListener().onTimelineObtained(_timeline);
			
		return Service.START_REDELIVER_INTENT;
	}

	@Override
	public void onSharedPreferenceChanged(SharedPreferences arg0, String arg1) {
		
		if(arg1.equals(YambaPreference.TIMELINEREFRESHRATE_PREFERENCE)) {

			_refreshDelay = Integer.parseInt(_preferences.getString(YambaPreference.TIMELINEREFRESHRATE_PREFERENCE, null));
		
			_timer.cancel();
			_timer.purge();
			_timer = new Timer();
			_timer.scheduleAtFixedRate(new GetTimelineTimerTask(), 0, _refreshDelay);

		}	
		else if(arg1.equals(YambaPreference.TIMELINEAUTOREFRESH_PREFERENCE)) {

			_enableAutoRefresh = _preferences.getBoolean(YambaPreference.TIMELINEAUTOREFRESH_PREFERENCE, false);
		
			_timer.cancel();
			_timer.purge();
			_timer = new Timer();
			
			if(_enableAutoRefresh)
				scheduleLooped();
			else
				scheduleOnce();
		}
		else if(arg1.equals(YambaPreference.TIMELINEMAXENTRIES_PREFERENCE)) {

			_savedEntriesCount = Integer.parseInt(_preferences.getString(YambaPreference.TIMELINEMAXENTRIES_PREFERENCE, null));
		}
	}
	
	private void scheduleLooped() {
		long curr = System.currentTimeMillis(),
			 left = (_lastSchedule == 0)?0:curr-_lastSchedule;
		
		if(left < 0) left = 0;
		
		_timer.scheduleAtFixedRate(new GetTimelineTimerTask(), left, _refreshDelay);		
		_lastSchedule = curr;
	}
	
	private void scheduleOnce() {
		long curr = System.currentTimeMillis(),
		     left = (_lastSchedule == 0)?0:curr-_lastSchedule;
			
		if(left < 0) left = 0;
		
		_timer.schedule(new GetTimelineTimerTask(), left);		
		_lastSchedule = curr;		
	}
	
	private class GetTimelineTimerTask extends TimerTask
	{

		@Override
		public void run() {
			
			Twitter connection = null;
			
			_timeline = null;
			
			
			List<Status> list =	null;
			
			try
			{
				connection = _twitterAsync.getInnerConnection();
				list = connection.getUserTimeline();
			}
			catch(Throwable e)
			{
				
			}
			
			if(list == null || list.size() == 0)
			{
				_timeline = Collections.EMPTY_LIST;
			}
			else
			{
				_timeline = new LazyLoadIterableAdapter<Status, Tweet>(list.subList(0, (list.size() >= _savedEntriesCount)?_savedEntriesCount:list.size()), StatusToTweetAdapter.Instance);
			}
			
			try {
				
				_source.open();
			
				if(_timeline != null)
				{
					for(Tweet tweet:_timeline)
					{
						_source.create(tweet);
					}
				}
				
				_timeline = _source.getAll();
				
				_source.close();
			
			} catch (SQLException e) {
				
			}
			
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
		
	}
}
