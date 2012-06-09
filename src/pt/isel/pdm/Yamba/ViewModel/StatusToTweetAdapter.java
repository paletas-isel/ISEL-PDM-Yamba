package pt.isel.pdm.yamba.ViewModel;

import pt.isel.pdm.patterns.adapter.Adapter;
import winterwell.jtwitter.Twitter.Status;

public class StatusToTweetAdapter implements Adapter<Status, Tweet> {
	
	public static final StatusToTweetAdapter Instance;
	
	static
	{
		Instance = new StatusToTweetAdapter();
	}
	
	private StatusToTweetAdapter()
	{
		
	}
	
	@Override
	public Tweet adapt(Status instance) {
		return Tweet.createFrom(instance);		
	}
}
