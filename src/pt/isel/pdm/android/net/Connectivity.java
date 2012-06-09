package pt.isel.pdm.android.net;

import pt.isel.pdm.utils.Preconditions;
import android.content.Context;
import android.net.ConnectivityManager;

public class Connectivity {

	private Connectivity()
	{
		
	}
	
	public static boolean isWifiConnected(Context context)
	{
		Preconditions.checkArgument(context != null);
		
		ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);

		return cm.getActiveNetworkInfo() != null && 
		       cm.getActiveNetworkInfo().isConnected();
	}
}
