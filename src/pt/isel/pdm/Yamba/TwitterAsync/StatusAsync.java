package pt.isel.pdm.Yamba.TwitterAsync;

import pt.isel.pdm.Yamba.TwitterAsync.helpers.StatusContainer;
import pt.isel.pdm.Yamba.TwitterAsync.listeners.StatusPublishedListener;
import winterwell.jtwitter.Twitter;

public class StatusAsync extends TwitterAsyncTask<StatusContainer, Void, Void> {
	
	private StatusPublishedListener _listener;
	
	public StatusAsync(Twitter connection, StatusPublishedListener listener) {
		super(connection);

		_listener = listener;
	}

	@Override
	protected Void doInBackground(StatusContainer... params) {
		Twitter connection = getConnection();
		
		for(StatusContainer status : params) {
			Twitter.Status s;			
			if(status.isReply()) {
				s = connection.updateStatus(status.getStatus(), status.inReplyTo());
			}
			else {
				s = connection.updateStatus(status.getStatus());
			}
			
			_listener.OnStatusPublished(s);
		}
		
		return null;
	}

}
