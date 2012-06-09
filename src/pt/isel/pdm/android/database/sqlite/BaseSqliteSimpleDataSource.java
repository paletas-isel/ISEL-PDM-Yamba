package pt.isel.pdm.android.database.sqlite;

import java.sql.SQLException;

import pt.isel.pdm.android.content.ObjectToContentValueAdapter;
import pt.isel.pdm.utils.Preconditions;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class BaseSqliteSimpleDataSource<T> implements SqliteSimpleDataSource<T> {

	private final SQLiteOpenHelper _helper;
	
	private final String _table; 
		
	private final CursorToObjectAdapter<T> _cursor2obj;
	
	private final ObjectToContentValueAdapter<T> _obj2content;
	
	private SQLiteDatabase _database;
	
	public BaseSqliteSimpleDataSource(SQLiteOpenHelper helper, String table, CursorToObjectAdapter<T> cursorAdapter, ObjectToContentValueAdapter<T> objAdapter)
	{
		Preconditions.checkArgument(helper != null);
		Preconditions.checkArgument(table != null);
		Preconditions.checkArgument(cursorAdapter != null);
		Preconditions.checkArgument(objAdapter != null);
		
		this._helper = helper;
		this._table = table;
		this._cursor2obj = cursorAdapter;
		this._obj2content = objAdapter;
	}
	
	@Override
	public synchronized void open() throws SQLException {
		
		if(_database == null)
		{
			_database = _helper.getWritableDatabase();
		}
	}

	@Override
	public synchronized void close() throws SQLException {
		
		if(_database != null)
		{
			_database.close();
			_database = null;
		}
	}

	@Override
	public boolean create(T value) {
		
		checkForDatabaseExistency();
		return _database.insert(_table, null, _obj2content.adapt(value)) != -1;
	}

	
	public Iterable<T> getAllOrderedBy(String orderBy)
	{
		return getAll(null, null, null, null, null, orderBy, IterableCursor.NO_LIMIT, 0);
	}
	
	@Override
	public Iterable<T> getAll() {
		throw new UnsupportedOperationException();		
	}
	
	private Iterable<T> getAll(String[] columns, String selection, String[] selectionArgs, String groupBy, String having, String orderBy, int limit, int offset)
	{
		checkForDatabaseExistency();
		
		Cursor cursor = _database.query(_table, columns, selection, selectionArgs, groupBy, having, orderBy);
		
		return new IterableCursor<T>(cursor, _cursor2obj, limit, offset);
	}

	@Override
	public Iterable<T> getSubGroup(int offset, int limit) {
		throw new UnsupportedOperationException();		
	}
	
	private void checkForDatabaseExistency()
	{
		Preconditions.checkNotNull(_database);
		Preconditions.checkState(_database.isOpen());
	}
}
