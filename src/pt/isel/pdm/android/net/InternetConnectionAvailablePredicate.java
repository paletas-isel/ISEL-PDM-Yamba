package pt.isel.pdm.android.net;

import pt.isel.pdm.utils.Preconditions;
import pt.isel.pdm.utils.Predicate;
import android.content.Context;

public class InternetConnectionAvailablePredicate implements Predicate<Object>
{
	private final Context _context;
	
	private InternetConnectionAvailablePredicate(Context context)
	{
		Preconditions.checkArgument(context != null);
		
		this._context = context;
	}
	
	@Override
	public boolean apply(Object arg0) 
	{
		///
		/// TODO: Change this with checking for Yamba API availability (access can be made by Wi-Fi/Ethernet/Others)
		///
		
		return Connectivity.isWifiConnected(_context);
	}
}
