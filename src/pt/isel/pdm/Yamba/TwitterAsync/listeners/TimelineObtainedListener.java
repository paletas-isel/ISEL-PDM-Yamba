package pt.isel.pdm.Yamba.TwitterAsync.listeners;

import java.util.List;

import winterwell.jtwitter.Twitter.Status;

public interface TimelineObtainedListener {

	public void onTimelineObtained(List<Status> timeline);
	
}
