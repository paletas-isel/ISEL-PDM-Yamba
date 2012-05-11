package pt.isel.pdm.yamba;

import java.text.SimpleDateFormat;

import pt.isel.pdm.yamba.ViewModel.Tweet;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class DetailsActivity extends Activity{

	private TextView _idText;
	private TextView _authorText;
	private TextView _statusText;
	private TextView _dateText;
	
	private static SimpleDateFormat _DateFormat;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.details);
		
		if(_DateFormat == null) {
			_DateFormat = new SimpleDateFormat(getString(R.string.dateFormat));
		}
		
		_idText = (TextView) findViewById(R.id.detailsId);
		_authorText = (TextView) findViewById(R.id.detailsAuthor);
		_statusText = (TextView) findViewById(R.id.detailsStatus);
		_dateText = (TextView) findViewById(R.id.detailsDate);
		
		onNewIntent(getIntent());
	}

	@Override
	protected void onNewIntent(Intent intent) {
		super.onNewIntent(intent);
		
		Tweet tweet = intent.getParcelableExtra(YambaApplication.TweetExtra);	
		
		if(tweet != null) {
			
			_idText.setText(String.format("%d", tweet.getId()));
			_authorText.setText(tweet.getAuthor());
			_statusText.setText(tweet.getStatus());
			_dateText.setText(_DateFormat.format(tweet.getPublicationDate()));
		}
		else {
			findViewById(R.layout.error).setVisibility(View.VISIBLE);
		}			
	}		
}
