package pt.isel.pdm.email.controller.async.adapters;

import pt.isel.pdm.email.Email;
import pt.isel.pdm.email.controller.EmailController;
import pt.isel.pdm.email.controller.async.EmailOperationCallback;
import pt.isel.pdm.email.controller.async.EmailOperationResult;
import pt.isel.pdm.utils.Preconditions;
import android.os.AsyncTask;

public class EmailControllerSendEmailAsyncTask extends AsyncTask<Email, Void, EmailOperationResult>{

	private final EmailController _controller;
	
	private final EmailOperationCallback _callback;
	
	public EmailControllerSendEmailAsyncTask(EmailController controller, EmailOperationCallback callback)
	{
		Preconditions.checkArgument(controller != null);
		Preconditions.checkArgument(callback != null);
		
		this._controller = controller;
		this._callback = callback;
	}
	
	@Override
	protected EmailOperationResult doInBackground(Email... params) {
		
		EmailOperationResult result = null;
		
		for(Email email:params)
		{
			result = doInBackground(email);
			
			if(EmailOperationResult.OK != result)
				return result;
		}
		
		return result;
	}
	
	private EmailOperationResult doInBackground(Email email) {
		
		try
		{
			_controller.sendEmail(email);
			return EmailOperationResult.OK;
		}
		catch(Throwable cause)
		{
			return new EmailOperationResult(cause);
		}
	}
	
	@Override
	protected void onPostExecute(EmailOperationResult result) {
		super.onPostExecute(result);
		
		_callback.onEmailOperationCompleted(result);
	}
	
	
	public EmailController getEmailController()
	{
		return _controller;
	}
	
	public EmailOperationCallback getCallback()
	{
		return _callback;
	}
}
