package pt.isel.pdm.yamba.TwitterAsync.services;

import java.lang.reflect.Array;
import java.util.Collection;
import java.util.LinkedList;

import pt.isel.pdm.android.net.Connectivity;
import pt.isel.pdm.yamba.TwitterAsync.TwitterAsync;
import pt.isel.pdm.yamba.TwitterAsync.helpers.StatusContainer;
import pt.isel.pdm.yamba.TwitterAsync.listeners.StatusPublishedListener;
import winterwell.jtwitter.Twitter;
import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.IBinder;
import android.util.Log;

public class StatusUploadService extends Service {
	
	public final static String PARAM_TAG = "PARAM_TAG";
		
	private boolean _isServiceDone;
	
	private Collection<StatusContainer> _toPublish;
	
	@Override
	public void onCreate() {

		Log.d("CREATE", "Create called");
		
		super.onCreate();
		
		_isServiceDone = true;
		_toPublish = new LinkedList<StatusContainer>();
	}

	@Override
	public IBinder onBind(Intent intent) {
		// TODO Auto-generated method stub
		return null;
	}

	 private BroadcastReceiver _wifiBroadcastReceiver
	   = new BroadcastReceiver(){

	  @Override
	  public void onReceive(Context arg0, Intent arg1) {
		  
		  NetworkInfo networkInfo = (NetworkInfo) arg1.getParcelableExtra(ConnectivityManager.EXTRA_NETWORK_INFO);
		   
		  if(networkInfo.getType() == ConnectivityManager.TYPE_WIFI)
		  {
			  if(networkInfo.isConnected())
			  {
				  _isServiceDone = publishPendentStatus();
			  }
		  }
	  }};

	private AsyncTask<StatusContainer, Twitter.Status, Void> _task ;
	
	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		
		StatusContainer params = intent.getParcelableExtra(PARAM_TAG);
		
		resetStatusUploadTask();
		
		if(params != null) _task.execute(params);
			
		return Service.START_REDELIVER_INTENT;
	}
	
	private synchronized void onFailedStatusPublishing(StatusContainer status)
	{
		_isServiceDone = false;
		
		if(_toPublish.size() == 0)
		{
			 this.registerReceiver(this._wifiBroadcastReceiver, new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION));
		}
		
		_toPublish.add(status);
		
		if(Connectivity.isWifiConnected(this))
			_isServiceDone = publishPendentStatus();
	}

	private synchronized boolean publishPendentStatus()
	{
		if(_toPublish.size() == 0)
			return true;
		
		try
		{
			resetStatusUploadTask();
			_task.execute((StatusContainer[]) _toPublish.toArray((StatusContainer[])Array.newInstance(StatusContainer.class,0)));
			this.unregisterReceiver(_wifiBroadcastReceiver);
			_isServiceDone = true;
			_toPublish.clear();
			return true;
		}
		catch(Throwable t)
		{
			return false;
		}
	}
	
	private void resetStatusUploadTask()
	{
		_task = new AsyncTask<StatusContainer, Twitter.Status, Void>() {

			private TwitterAsync _twitterAsync = TwitterAsync.connect();
			
			@Override
			protected Void doInBackground(StatusContainer... params) {
				
				Twitter.Status s;			
				
				Twitter connection = null;
				
				if(Connectivity.isWifiConnected(StatusUploadService.this))
				{
					try
					{
						connection = _twitterAsync.getInnerConnection();
					}
					catch(Throwable t)
					{
						
					}
				}
				
				for(StatusContainer status : params) {
					
					s = null;
					
					try
					{
						if(connection != null)
						{
							if(status.isReply()) {
								s = connection.updateStatus(status.getStatus(), status.inReplyTo());
							}
							else {
								s = connection.updateStatus(status.getStatus());
							}
						}
					}
					catch(Throwable t)
					{
						s = null;
					}
					
					if(s == null)
					{
						onFailedStatusPublishing(status);
					}
					
					publishProgress(s);
					
				}
				
				if(_isServiceDone)
					stopSelf();
				
				return null;
			}

			@Override
			protected void onProgressUpdate(Twitter.Status... values) {

				StatusPublishedListener listener = _twitterAsync.getStatusPublishedListener();
				if(listener != null) {
					for(Twitter.Status status : values) {
						listener.onStatusPublished(status);
					}
				}
				
				super.onProgressUpdate(values);
			}	
		};
	}
}
