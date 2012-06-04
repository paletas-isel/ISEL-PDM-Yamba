package pt.isel.pdm.email.controller.async;

import pt.isel.pdm.email.Email;

public interface AsyncEmailController {

	public void sendEmail(Email email, EmailOperationCallback callback);
}
