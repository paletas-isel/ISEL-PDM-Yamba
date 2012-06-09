package pt.isel.pdm.android.database.sqlite;

import pt.isel.pdm.patterns.adapter.Adapter;
import android.database.Cursor;

public interface CursorToObjectAdapter<T> extends Adapter<Cursor, T>{
	
}
