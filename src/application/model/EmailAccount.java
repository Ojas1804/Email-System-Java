package application.model;

import java.util.Properties;
import javax.mail.Store;

public class EmailAccount
{
	private String emailId;
	private String password;
	private Properties properties;  //  To hold email configurations
	private Store mailStore;   // for storing and retrieving mails
	
	
	public EmailAccount(String emailId, String password)
	{
		super();
		this.emailId = emailId;
		this.password = password;
		
		this.properties = new Properties();
//		this.properties.put("incomingHost", "imap.gmail.com");
		this.properties.put("incomingHost", "imap.gmx.com");
		this.properties.put("mail.store.protocol", "imaps");
//		
		this.properties.put("mail.transfer.protocol", "smtps");
//		this.properties.put("mail.smtps.host", "smtps.gmail.com");
		this.properties.put("mail.smtp.host", "mail.gmx.com");
		this.properties.put("mail.smtps.auth", "true");
//		this.properties.put("outgoingHost", "smtps.gmail.com");
		this.properties.put("outgoingHost", "mail.gmx.com");
	}


	public String getEmailId()
	{
		return emailId;
	}


	public String getPassword()
	{
		return password;
	}


	public Properties getProperties()
	{
		return properties;
	}


	public void setProperties(Properties properties)
	{
		this.properties = properties;
	}


	public Store getMailStore()
	{
		return mailStore;
	}


	public void setMailStore(Store mailStore)
	{
		this.mailStore = mailStore;
	}
}
