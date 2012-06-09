package pt.isel.pdm.yamba;

import java.text.SimpleDateFormat;
import java.util.Collections;

import pt.isel.pdm.email.Email;
import pt.isel.pdm.email.controller.EmailAppEmailController;
import pt.isel.pdm.email.controller.async.AsyncEmailController;
import pt.isel.pdm.email.controller.async.EmailOperationCallback;
import pt.isel.pdm.email.controller.async.EmailOperationResult;
import pt.isel.pdm.email.controller.async.adapters.EmailControllerToAsyncAdapter;
import pt.isel.pdm.yamba.ViewModel.Tweet;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class DetailsActivity extends Activity implements View.OnClickListener, EmailOperationCallback{

	private AsyncEmailController _email;
	private TextView _idText;
	private TextView _authorText;
	private TextView _statusText;
	private TextView _dateText;
	private Button _button;
	
	private static SimpleDateFormat _DateFormat;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.details);
		
		if(_DateFormat == null) {
			_DateFormat = new SimpleDateFormat(getString(R.string.dateFormat));
		}
		
		_email = new EmailControllerToAsyncAdapter(new EmailAppEmailController(this));
		
		_idText = (TextView) findViewById(R.id.detailsId);
		_authorText = (TextView) findViewById(R.id.detailsAuthor);
		_statusText = (TextView) findViewById(R.id.detailsStatus);
		_dateText = (TextView) findViewById(R.id.detailsDate);
		_button = (Button) findViewById(R.id.sendEmailButton);
		
		_button.setOnClickListener(this);
		
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

	@Override
	public void onClick(View v) {
		
		if(v == _button)
		{
			Email email = new Email()
			{
				@Override
				public Iterable<String> getRecipients() {
					return Collections.EMPTY_LIST;
				}

				@Override
				public String getSubject() {
					return "Tweet";
				}

				@Override
				public String getText() {
					return _statusText.getText().toString();
				}
			};
			
			_email.sendEmail(email,this);
		}
	}

	@Override
	public void onEmailOperationCompleted(EmailOperationResult result) {
				
	}		
}
