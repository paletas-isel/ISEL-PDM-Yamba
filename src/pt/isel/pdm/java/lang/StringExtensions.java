package pt.isel.pdm.java.lang;

public class StringExtensions {

	private StringExtensions()
	{
		
	}
	
	public static String arrayToString(String[] array)
	{
		StringBuffer buffer = new StringBuffer();
		
		for(String string:array)
		{
			buffer.append(string);
		}
		
		return buffer.toString();
	}
}
