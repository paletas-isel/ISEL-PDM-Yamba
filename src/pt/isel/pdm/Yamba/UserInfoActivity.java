package pt.isel.pdm.yamba;

import android.os.Bundle;
import android.view.Menu;
import android.widget.TextView;


public class UserInfoActivity extends MenuActivity
{
	private IUserInfoParams _params;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.userinfo);
		
		fillActivityWithUserInfo();
	}
	
	@Override
    protected void doModifyMenu(Menu menu) {
    	
    	enableMenuItem(menu, R.id.show_user_info);
    }
	
	private void fillActivityWithUserInfo()
	{
		((TextView) findViewById(R.id.userInfoName)).setText(_params.getUserName());
		((TextView) findViewById(R.id.userInfoName)).setText(Integer.toString(_params.getStatusCount()));
		((TextView) findViewById(R.id.userInfoName)).setText(Integer.toString(_params.getSubscribersCount()));
		((TextView) findViewById(R.id.userInfoName)).setText(Integer.toString(_params.getSubscriptionsCount()));
	}
}
