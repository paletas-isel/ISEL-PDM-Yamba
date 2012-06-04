package pt.isel.pdm.email;

public interface Email {

	public Iterable<String> getRecipients();
	
	public String getSubject();
	
	public String getText();
}
