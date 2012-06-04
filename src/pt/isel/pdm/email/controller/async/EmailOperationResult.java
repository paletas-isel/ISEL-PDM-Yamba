package pt.isel.pdm.email.controller.async;

public class EmailOperationResult {

	public static final EmailOperationResult OK;
	
	static
	{
		OK = new EmailOperationResult();
	}
	
	private final Throwable _cause;
	
	public EmailOperationResult(Throwable cause)
	{
		this._cause = cause;
	}
	
	private EmailOperationResult()
	{
		this(null);
	}
	
	public Throwable getCause()
	{
		return _cause;
	}
}
