package pt.isel.pdm.Yamba.TwitterAsync;

import winterwell.jtwitter.Twitter;
import android.os.AsyncTask;

public abstract class TwitterAsyncTask<A, B, C> extends AsyncTask<A, B, C>{

	private Twitter _connection;
	
	public TwitterAsyncTask(Twitter connection) {
		_connection = connection;
	}
	
	protected Twitter getConnection() {
		return _connection;
	}
	
}
