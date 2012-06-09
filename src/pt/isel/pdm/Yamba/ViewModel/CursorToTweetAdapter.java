package pt.isel.pdm.yamba.ViewModel;

import java.util.Date;

import pt.isel.pdm.android.database.sqlite.CursorToObjectAdapter;
import android.database.Cursor;

public class CursorToTweetAdapter implements CursorToObjectAdapter<Tweet>{
	
	public static final CursorToTweetAdapter Instance;
	
	static
	{
		Instance = new CursorToTweetAdapter();
	}
	
	private CursorToTweetAdapter()
	{
		
	}
	
	@Override
	public Tweet adapt(Cursor instance) {
		
		return new Tweet(
			instance.getLong(TweetsSqliteHelper.COLUMN_ID_POSITION),
			instance.getString(TweetsSqliteHelper.COLUMN_AUTHOR_POSITION),
			instance.getString(TweetsSqliteHelper.COLUMN_STATUS_POSITION),
			new Date(instance.getLong(TweetsSqliteHelper.COLUMN_CREATED_AT_POSITION))
		);
	}
}
