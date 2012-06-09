package pt.isel.pdm.email.controller.async.decorators;

import pt.isel.pdm.email.Email;
import pt.isel.pdm.email.controller.async.AsyncEmailController;
import pt.isel.pdm.email.controller.async.EmailOperationCallback;
import pt.isel.pdm.email.controller.async.EmailOperationResult;
import pt.isel.pdm.utils.Preconditions;
import pt.isel.pdm.utils.Predicate;

public class AsyncEmailControllerPredicateValidationDecorator implements AsyncEmailController{

	private final AsyncEmailController _controller;
	
	private final Predicate<Email> _predicate;
	
	public AsyncEmailControllerPredicateValidationDecorator(Predicate<Email> predicate, AsyncEmailController controller)
	{
		Preconditions.checkArgument(controller != null);
		Preconditions.checkArgument(predicate != null);
		
		this._controller = controller;
		this._predicate = predicate;
	}
	
	@Override
	public boolean sendEmail(Email email, EmailOperationCallback callback) {
		
		try
		{
			if(_predicate.apply(email))
			{			
				_controller.sendEmail(email, callback);
				return true;
			}
		}
		catch(Throwable e)
		{
			callback.onEmailOperationCompleted(new EmailOperationResult(e));
		}
		
		return false;
	}
}
