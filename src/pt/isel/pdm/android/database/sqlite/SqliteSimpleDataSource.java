package pt.isel.pdm.android.database.sqlite;

import java.sql.SQLException;

public interface SqliteSimpleDataSource<T> {

	public void open() throws SQLException;
	
	public void close() throws SQLException;
	
	public boolean create(T value);
	
	public Iterable<T> getAll();
	
	public Iterable<T> getSubGroup(int offset, int limit);
}
