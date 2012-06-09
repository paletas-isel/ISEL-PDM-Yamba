package pt.isel.pdm.email.controller;

import pt.isel.pdm.email.Email;
import pt.isel.pdm.java.lang.Iterables;
import pt.isel.pdm.utils.Preconditions;
import pt.isel.pdm.yamba.R;
import android.content.Context;
import android.content.Intent;

public class EmailAppEmailController implements EmailController{

	private final Context _context;
		
	public EmailAppEmailController(Context context)
	{
		Preconditions.checkArgument(context != null);
		
		this._context = context;
	}
	
	@Override
	public void sendEmail(Email email) {
		
		Intent intent = new Intent(Intent.ACTION_SEND); 
		intent.setType("text/plain");
		intent.putExtra(Intent.EXTRA_SUBJECT, email.getSubject());
		intent.putExtra(Intent.EXTRA_TEXT, email.getText());
		intent.putExtra(Intent.EXTRA_EMAIL, Iterables.toArray(email.getRecipients(), String.class));
		intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		
		_context.startActivity(Intent.createChooser(intent, _context.getString(R.string.emailChooserLabel)));
	}
}
