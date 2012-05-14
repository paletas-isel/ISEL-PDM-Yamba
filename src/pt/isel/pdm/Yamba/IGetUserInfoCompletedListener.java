package pt.isel.pdm.yamba;

public interface IGetUserInfoCompletedListener {

	public void onGetUsernameCompleted(String username);
	
	public void onGetStatusCountCompleted(int status);
	
	public void onGetSubscribersCountCompleted(int subscribers);
	
	public void onGetSubscriptionsCountCompleted(int subscriptions);
}
