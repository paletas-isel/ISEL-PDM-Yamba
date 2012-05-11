package pt.isel.pdm.yamba.TwitterAsync.listeners;

import java.util.List;

import winterwell.jtwitter.Twitter.Status;

public interface TimelineObtainedListener {

	public void onTimelineObtained(List<Status> timeline);
	
}
