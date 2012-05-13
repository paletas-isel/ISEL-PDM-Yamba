package pt.isel.pdm.yamba.TwitterAsync.services;

import pt.isel.pdm.yamba.IUserInfoParams;
import pt.isel.pdm.yamba.TwitterAsync.TwitterAsync;
import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

public class UserInfoPullService extends Service implements IUserInfoParams{

	@Override
	public IBinder onBind(Intent intent) {
		return null;
	}
	
	@Override
	public void onCreate() {
		super.onCreate();
	}
	
	@Override
	public void onDestroy() {
		
		super.onDestroy();
	}

	@Override
	public String getUserName() {
		return TwitterAsync.getUsername();
	}

	@Override
	public int getStatusCount() {
		return TwitterAsync.connect().getInnerConnection().getUserTimeline().size();
	}

	@Override
	public int getSubscribersCount() {
		return TwitterAsync.connect().getInnerConnection().getFollowerIDs().size();
	}

	@Override
	public int getSubscriptionsCount() {
		return 1234567;
	}
}
