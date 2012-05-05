package pt.isel.pdm.Yamba.TwitterAsync.listeners;

import pt.isel.pdm.Yamba.exceptions.TwitterException;

public interface TwitterExceptionListener {

	void onExceptionThrown(TwitterException e);
	
}
