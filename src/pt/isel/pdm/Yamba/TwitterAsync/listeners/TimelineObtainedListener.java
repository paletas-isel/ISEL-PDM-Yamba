package pt.isel.pdm.yamba.TwitterAsync.listeners;

import pt.isel.pdm.yamba.ViewModel.Tweet;

public interface TimelineObtainedListener {

	public void onTimelineObtained(Iterable<Tweet> timeline);
	
}
