package pt.isel.pdm.yamba.TwitterAsync.services;

import pt.isel.pdm.yamba.TwitterAsync.TwitterAsync;
import pt.isel.pdm.yamba.TwitterAsync.helpers.StatusContainer;
import pt.isel.pdm.yamba.TwitterAsync.listeners.StatusPublishedListener;
import winterwell.jtwitter.Twitter;
import android.app.Service;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.IBinder;
import android.util.Log;

public class StatusUploadService extends Service {
	
	public final static String PARAM_TAG = "PARAM_TAG";
		
	@Override
	public void onCreate() {

		Log.d("CREATE", "Create called");
		
		super.onCreate();
	}

	@Override
	public IBinder onBind(Intent intent) {
		// TODO Auto-generated method stub
		return null;
	}

	private AsyncTask<StatusContainer, Twitter.Status, Void> _task = new AsyncTask<StatusContainer, Twitter.Status, Void>() {

		private TwitterAsync _twitterAsync = TwitterAsync.connect();
		
		@Override
		protected Void doInBackground(StatusContainer... params) {
			
			Twitter connection = _twitterAsync.getInnerConnection();

			Twitter.Status s;			
			
			for(StatusContainer status : params) {
				if(status.isReply()) {
					s = connection.updateStatus(status.getStatus(), status.inReplyTo());
				}
				else {
					s = connection.updateStatus(status.getStatus());
				}
				
				publishProgress(s);
			}
			
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
	
	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		
		StatusContainer params = intent.getParcelableExtra(PARAM_TAG);
		
		if(params != null) _task.execute(params);
			
		return Service.START_REDELIVER_INTENT;
	}
	
	
}
