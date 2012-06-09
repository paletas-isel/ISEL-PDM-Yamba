package pt.isel.pdm.android.content;

import pt.isel.pdm.utils.Preconditions;
import android.content.ContentValues;

public class ContentValuesExtension {
	
	private ContentValuesExtension()
	{
		
	}
	
	public static boolean put(ContentValues content, String key, Object obj)
	{
		Preconditions.checkArgument(content != null);
		Preconditions.checkArgument(key != null);
		
		if(obj == null)
		{
			content.putNull(key);
			return true;
		}
		
		///
		/// TODO support for the other classes
		///
		
		if(String.class == obj.getClass())
		{
			content.put(key, (String)obj);
		}
		else if(Integer.class == obj.getClass())
		{
			content.put(key, (Integer)obj);
		}
		else
		{
			return false;
		}
		
		return true;
	}
}
