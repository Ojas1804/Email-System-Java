package application.model;

import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import javax.mail.Session;
import javax.mail.Store;

public class EmailAccount
{
	private String emailId;
	private String password;
	private Properties properties;  //  To hold email configurations
	private Store mailStore;   // for storing and retrieving mails
	private Session session;
	
	private Map<String, String> imapServers = new HashMap<String, String>();
	private Map<String, String> smtpServers = new HashMap<String, String>();
	
	
	public EmailAccount(String emailId, String password)
	{
		super();
		this.emailId = emailId;
		this.password = password;
		initializeImapServers();
		initializeSmtpServers();
		String mailOperator = getMailOperator();
		
		this.properties = new Properties();
		this.properties.put("incomingHost", imapServers.get(mailOperator));
		this.properties.put("mail.store.protocol", "imaps");
		
		this.properties.put("mail.transfer.protocol", "smtp");
		this.properties.put("mail.smtp.host", smtpServers.get(mailOperator));
		this.properties.put("mail.smtp.user", emailId);
		this.properties.put("mail.smtp.password", password);
		this.properties.put("mail.smtp.auth", "true");
		this.properties.put("outgoingHost", smtpServers.get(mailOperator));
		
		this.properties.put("mail.smtp.port", "587");
	    this.properties.put("mail.smtp.socketFactory.port", "587");
		this.properties.put("mail.smtp.starttls.enable", "true");
	}

	
	private String getMailOperator()
	{
		String[] temp = emailId.split("@");
		String[] domain = temp[1].split("\\.");
		return domain[0];
	}


	private void initializeImapServers()
	{
		imapServers.put("gmail", "imap.gmail.com");
		imapServers.put("gmx", "imap.gmx.com");
		imapServers.put("hotmail", "outlook.office365.com");
		imapServers.put("outlook", "outlook.office365.com");
		imapServers.put("zohomail", "imap.zoho.in");
		imapServers.put("yandex", "imap.yandex.com");
	}
	

	private void initializeSmtpServers()
	{
		smtpServers.put("gmail", "smtps.gmail.com");
		smtpServers.put("gmx", "mail.gmx.com");
		smtpServers.put("hotmail", "smtp-mail.outlook.com");
		smtpServers.put("outlook", "smtp-mail.outlook.com");
		smtpServers.put("zohomail", "smtp.zoho.in");
		smtpServers.put("yandex", "smtp.yandex.com");
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


	public Session getSession()
	{
		return session;
	}


	public void setSession(Session session)
	{
		this.session = session;
	}


	@Override
	public String toString()
	{
		return emailId;
	}
}
