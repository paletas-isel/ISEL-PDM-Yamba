package pt.isel.pdm.utils;

public class Preconditions {

	private Preconditions()
	{
		
	}
	
	public static void checkArgument(boolean expression)
	{
		if(!expression)
			throw new IllegalArgumentException();
	}
	
	public static void checkState(boolean expression)
	{
		if(!expression)
			throw new IllegalStateException();
	}
	
	public static void checkNotNull(Object obj)
	{
		if(obj == null)
			throw new NullPointerException();
	}
}
