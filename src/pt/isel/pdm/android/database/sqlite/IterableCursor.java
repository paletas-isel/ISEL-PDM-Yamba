package pt.isel.pdm.android.database.sqlite;

import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedList;

import pt.isel.pdm.utils.Preconditions;
import android.database.Cursor;

public class IterableCursor<T> implements Iterable<T>{

	public static final int NO_LIMIT = -1;

	private final Iterable<T> _iterable;
	
	public IterableCursor(Cursor cursor, CursorToObjectAdapter<T> adapter, int limit, int offset)
	{
		_iterable = createIterableAndFillItWith(cursor, adapter, limit, offset);		
	}
	
	public IterableCursor(Cursor cursor, CursorToObjectAdapter<T> adapter, int limit)
	{
		this(cursor, adapter, limit, 0);
	}
	
	public IterableCursor(Cursor cursor, CursorToObjectAdapter<T> adapter)
	{
		this(cursor, adapter, NO_LIMIT);
	}

	@Override
	public Iterator<T> iterator() {
		
		return _iterable.iterator();
	}
	
	public static <T> Iterable<T> createIterableAndFillItWith(Cursor cursor, CursorToObjectAdapter<T> adapter, int limit, int offset)
	{
		Preconditions.checkArgument(cursor != null);
		Preconditions.checkArgument(adapter != null);
		Preconditions.checkArgument(offset >= 0);
		Preconditions.checkArgument(!cursor.isClosed());
		
		Collection<T> collection = new LinkedList<T>();
		
		if(cursor.moveToPosition(offset))
		{
			while(!cursor.isAfterLast() && limit != collection.size())
			{
				collection.add(adapter.adapt(cursor));
				cursor.moveToNext();
			}
		}
		
		return collection;
	}
}
