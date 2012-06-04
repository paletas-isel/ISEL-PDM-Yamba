package pt.isel.pdm.email.controller;

import pt.isel.pdm.email.Email;
import pt.isel.pdm.yamba.R;
import android.content.Context;
import android.content.Intent;

import com.google.common.base.Preconditions;

public class EmailAppEmailController implements EmailController{

	private final Context _context;
		
	public EmailAppEmailController(Context context)
	{
		Preconditions.checkArgument(context != null);
		
		this._context = context;
	}
	
	@Override
	public void sendEmail(Email email) {
		
		Intent intent = new Intent(Intent.ACTION_SENDTO); 
		intent.setType("text/plain");
		intent.putExtra(Intent.EXTRA_SUBJECT, email.getSubject());
		intent.putExtra(Intent.EXTRA_TEXT, email.getText());
		intent.putExtra(Intent.EXTRA_EMAIL  , new String[]{"ricardomiguel.sn@gmail.com"});
		intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		
		_context.startActivity(Intent.createChooser(intent, _context.getString(R.string.emailChooserLabel)));
	}
}
