package pt.isel.pdm.yamba.ViewModel;

import pt.isel.pdm.android.content.ObjectToContentValueAdapter;
import android.content.ContentValues;

public class TweetToContentValueAdapter implements ObjectToContentValueAdapter<Tweet> {

	public static final TweetToContentValueAdapter Instance;
	
	static
	{
		Instance = new TweetToContentValueAdapter();
	}
	
	private TweetToContentValueAdapter()
	{
		
	}
	
	@Override
	public ContentValues adapt(Tweet instance) {
		
		ContentValues values = new ContentValues();
		
		if(instance != null)
		{
			values.put(TweetsSqliteHelper.COLUMN_ID, instance.getId());
			values.put(TweetsSqliteHelper.COLUMN_AUTHOR, instance.getAuthor());
			values.put(TweetsSqliteHelper.COLUMN_STATUS, instance.getStatus());
			values.put(TweetsSqliteHelper.COLUMN_CREATED_AT, instance.getPublicationDate().getTime());
		}
		
		return values;
	}
}
