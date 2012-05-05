package pt.isel.pdm.Yamba;

import android.app.Activity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.content.Intent;

public abstract class MenuActivity extends Activity{

	@Override
    public boolean onCreateOptionsMenu(Menu menu) {
    	MenuInflater inflater = getMenuInflater();
    	inflater.inflate(R.menu.menu, menu);
    	
    	doModifyMenu(menu);
    	    	
    	return true;
    }
	
	protected void doModifyMenu(Menu menu) { }
	
	protected boolean enableMenuItem(Menu menu, int id) {
		
		MenuItem item = menu.findItem(id);
		
		if(item.isEnabled()) return false;
		
		item.setEnabled(true);
		return true;		
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {	   
		Intent intent;
		switch (item.getItemId()) {
	        case R.id.create_status:     
	        	intent = new Intent(this, StatusActivity.class);
	    		startActivity(intent);	        	
	            break;
	        case R.id.show_timeline:     
	        	intent = new Intent(this, TimelineActivity.class);
	    		startActivity(intent);
	        	break;
	        case R.id.show_settings:
	        	intent = new Intent(this, YambaPreference.class);
	    		startActivity(intent);	        	
	        	break;
	    }		
	    return true;
	}
}
