package pt.isel.pdm.yamba.ViewModel;

import pt.isel.pdm.java.lang.StringExtensions;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class TweetsSqliteHelper extends SQLiteOpenHelper{

	public static final String 
		COLUMN_ID = "_id",
		COLUMN_AUTHOR  = "_author",
		COLUMN_STATUS = "_status",
		COLUMN_CREATED_AT = "_created_at";

	public static final int
		COLUMN_ID_POSITION = 0,
		COLUMN_AUTHOR_POSITION = 1,
		COLUMN_STATUS_POSITION = 2,
		COLUMN_CREATED_AT_POSITION = 3;
	
	static final String TABLE_NAME = "tweets";
	
	private static final String DATABASE_NAME = "tweets.db";
	
	private static final int DATABASE_VERSION = 1;

	private static final String DATABASE_CREATE;
	
	static
	{
		DATABASE_CREATE = getStringForDataBaseCreate
		(
			TABLE_NAME,
			new SqliteFieldCreationWithPositionContainer(
				COLUMN_ID_POSITION,
				String.format("%s integer primary key", COLUMN_ID)
			),
			new SqliteFieldCreationWithPositionContainer(
				COLUMN_AUTHOR_POSITION,
				String.format("%s text not null", COLUMN_AUTHOR)
			),
			new SqliteFieldCreationWithPositionContainer(
				COLUMN_STATUS_POSITION,
				String.format("%s text not null", COLUMN_STATUS)
			),
			new SqliteFieldCreationWithPositionContainer(
				COLUMN_CREATED_AT_POSITION,
				String.format("%s integer primary key", COLUMN_CREATED_AT)
			)
		);
	}
	
	public TweetsSqliteHelper(Context context)
	{
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
	}
	
	@Override
	public void onCreate(SQLiteDatabase db) {
		db.execSQL(DATABASE_CREATE);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		
		db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
		onCreate(db);
	}
	
	private static String getStringForDataBaseCreate(String table, SqliteFieldCreationWithPositionContainer... container)
	{
		String[] fields = new String[container.length];
		
		for(SqliteFieldCreationWithPositionContainer field:container)
		{
			fields[field.Position] = field.Query;
			
			if(field.Position != fields.length - 1)
				fields[field.Position] += ",";
		}
				
		return String.format(
				"create table %s(" 
				+ "%s"
				+ ");",
				table,
				StringExtensions.arrayToString(fields)
		);
	}
	
	private static class SqliteFieldCreationWithPositionContainer
	{
		public int Position;
		
		public String Query;
		
		public SqliteFieldCreationWithPositionContainer(int position, String query)
		{
			this.Position = position;
			this.Query = query;
		}
	}
}
