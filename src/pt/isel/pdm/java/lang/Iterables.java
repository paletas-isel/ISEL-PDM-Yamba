package pt.isel.pdm.java.lang;

import java.lang.reflect.Array;
import java.util.LinkedList;

public class Iterables {

	private Iterables()
	{
		
	}
	
	public static <T> T[] toArray(Iterable<T> iterable, Class<T> klass)
	{
		LinkedList<T> list = new LinkedList<T>();
		
		for(T value:iterable)
			list.add(value);
		
		return list.toArray((T[])Array.newInstance(klass, list.size()));
	}
}
