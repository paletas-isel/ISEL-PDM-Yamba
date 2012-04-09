package pt.isel.pdm.Yamba.TwitterAsync;

import java.lang.ref.WeakReference;
import pt.isel.pdm.Yamba.TwitterAsync.helpers.StatusContainer;
import pt.isel.pdm.Yamba.TwitterAsync.listeners.StatusPublishedListener;

import winterwell.jtwitter.Twitter;
import winterwell.jtwitter.TwitterException;

public class TwitterAsync extends Twitter{
	
	private WeakReference<StatusPublishedListener> _statusPublishedListener;
	
	private static String _serviceUri;
	
	public synchronized static void setServiceUri(String uri) {
		if(uri == null)
			_Instance = null;
		
		_serviceUri = uri;
	}
	
	private TwitterAsync(String username, String password) {
		super(username, password);
	}
	
	public synchronized void setStatusPublishedListener(StatusPublishedListener listener) {
				
		_statusPublishedListener = new WeakReference<StatusPublishedListener>(listener);		
	
	}
	
	public synchronized void clearStatusPublishedListener() {
		_statusPublishedListener = null;
	}	
	
	public void updateStatusAsync(String statusText, long inReplyToStatusId)
			throws TwitterException {

		StatusAsync task = new StatusAsync(this, _statusPublishedListener.get());
		task.execute(StatusContainer.create(statusText, inReplyToStatusId));
	}

	public void updateStatusAsync(String statusText) {		

		StatusAsync task = new StatusAsync(this, _statusPublishedListener.get());
		task.execute(StatusContainer.create(statusText));
	}	
	
	/*
	 * SINGLETON
	 */
	
	private static TwitterAsync _Instance;
	
	public synchronized static TwitterAsync connect(String username, String password) {
		
		if((_Instance == null || !_Instance.isValidLogin()) && _serviceUri != null) {
			_Instance = new TwitterAsync(username, password);
			_Instance.setAPIRootUrl(_serviceUri);
		}
		
		return _Instance;
	}
}
