package pt.isel.pdm.email.controller.async.adapters;

import pt.isel.pdm.email.Email;
import pt.isel.pdm.email.controller.EmailController;
import pt.isel.pdm.email.controller.async.AsyncEmailController;
import pt.isel.pdm.email.controller.async.EmailOperationCallback;
import pt.isel.pdm.utils.Preconditions;

public class EmailControllerToAsyncAdapter implements AsyncEmailController{

	private final EmailController _controller;
	
	public EmailControllerToAsyncAdapter(EmailController controller)
	{
		Preconditions.checkArgument(controller != null);
		
		this._controller = controller;
	}
	
	@Override
	public boolean sendEmail(Email email, EmailOperationCallback callback) {
		new EmailControllerSendEmailAsyncTask(_controller, callback).doInBackground(email);
		return true;
	}
}
