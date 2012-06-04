package pt.isel.pdm.email.controller.async;


public interface EmailOperationCallback {

	public void onEmailOperationCompleted(EmailOperationResult result);
}
