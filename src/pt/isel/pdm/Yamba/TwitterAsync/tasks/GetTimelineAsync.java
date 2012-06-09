package pt.isel.pdm.yamba.TwitterAsync.tasks;

import java.util.List;

import pt.isel.pdm.java.lang.LazyLoadIterableAdapter;
import pt.isel.pdm.utils.Preconditions;
import pt.isel.pdm.yamba.TwitterAsync.TwitterAsync;
import pt.isel.pdm.yamba.TwitterAsync.TwitterAsyncTask;
import pt.isel.pdm.yamba.TwitterAsync.listeners.TimelineObtainedListener;
import pt.isel.pdm.yamba.ViewModel.StatusToTweetAdapter;
import pt.isel.pdm.yamba.ViewModel.Tweet;
import pt.isel.pdm.yamba.ViewModel.TweetsSqliteDataSource;
import winterwell.jtwitter.Twitter;

public class GetTimelineAsync extends TwitterAsyncTask<String, Iterable<Tweet>, Void>{
	
	private final TweetsSqliteDataSource _source;
	
	public GetTimelineAsync(TwitterAsync connection, TweetsSqliteDataSource source) {
		super(connection);
		
		Preconditions.checkArgument(source != null);
		
		this._source = source;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	protected Void doExecute(String... arg0) {

		Twitter connection = getConnection();

		List<winterwell.jtwitter.Twitter.Status> userTimeline = null;
		
		if(arg0 != null && arg0.length > 0) {	
			
			for(String user : arg0) {
				userTimeline = connection.getUserTimeline(user);			
			}
		}
		else {
			
			userTimeline = connection.getUserTimeline();			
		}
		
		if(userTimeline != null)
		{
			Iterable<Tweet> tweets = new LazyLoadIterableAdapter<winterwell.jtwitter.Twitter.Status, Tweet>(userTimeline, StatusToTweetAdapter.Instance);
			
			for(Tweet tweet:tweets)
			{
				_source.create(tweet);
			}
		}
		
		onProgressUpdate(_source.getAll());
		
		return null;		
	}

	@Override
	protected void onProgressUpdate(Iterable<Tweet>... values) {

		TimelineObtainedListener listener = getTwitterAsync().getTimelineObtainedListener();
		if(listener != null) {
			for(Iterable<Tweet> tweets : values) {
				listener.onTimelineObtained(tweets);
			}		
		}
		
		super.onProgressUpdate(values);
	}
}
