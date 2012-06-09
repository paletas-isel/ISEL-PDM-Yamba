package pt.isel.pdm.yamba.ViewModel;

import pt.isel.pdm.android.database.sqlite.BaseSqliteSimpleDataSource;
import android.content.Context;

public class TweetsSqliteDataSource extends BaseSqliteSimpleDataSource<Tweet>{

	public TweetsSqliteDataSource(Context context) {
		super(new TweetsSqliteHelper(context), TweetsSqliteHelper.TABLE_NAME, CursorToTweetAdapter.Instance, TweetToContentValueAdapter.Instance);
	}
	
	@Override
	public Iterable<Tweet> getAll() {
		return super.getAllOrderedBy(TweetsSqliteHelper.COLUMN_CREATED_AT + " DESC");
	}
}
