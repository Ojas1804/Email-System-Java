package application.model.services;

import javax.mail.AuthenticationFailedException;
import javax.mail.Authenticator;
import javax.mail.MessagingException;
import javax.mail.NoSuchProviderException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Store;

import application.EmailManager;
import application.model.EmailAccount;
import application.model.LoginResult;
import javafx.concurrent.Service;
import javafx.concurrent.Task;


public class LoginService extends Service<LoginResult>
{
	EmailAccount emailAccount;
	EmailManager emailManager;
	
	
	public LoginService(EmailAccount emailAccount, EmailManager emailManager)
	{
		super();
		this.emailAccount = emailAccount;
		this.emailManager = emailManager;
	}
	
	
	private LoginResult login()
	{
		Authenticator authenticator = new Authenticator() 
		{
			@Override
			protected PasswordAuthentication getPasswordAuthentication()
			{
				return new PasswordAuthentication(emailAccount.getEmailId(), emailAccount.getPassword());
			}
		};
		
		try
		{
			Session session = Session.getInstance(emailAccount.getProperties(), authenticator);
			emailAccount.setSession(session);
			Store store = session.getStore("imaps");
			store.connect(emailAccount.getProperties().getProperty("incomingHost"), emailAccount.getEmailId(),
					emailAccount.getPassword());
			emailAccount.setMailStore(store);   // use to get mails
			emailManager.addEmailAccount(emailAccount);
		}
		catch(NoSuchProviderException e)
		{
			e.printStackTrace();
			return LoginResult.FAILED_BY_NETWORK;
		}
		catch(AuthenticationFailedException e)
		{
			e.printStackTrace();
			return LoginResult.FAILED_BY_CREDENTIALS;
		}
		catch(MessagingException e)
		{
			e.printStackTrace();
			return LoginResult.FAILED_BY_UNEXPECTED_ERROR;
		}
		catch(Exception e)
		{
			e.printStackTrace();
			return LoginResult.FAILED_BY_UNEXPECTED_ERROR;
		}
		
		return LoginResult.SUCCESS;
	}


	
	@Override
	protected Task<LoginResult> createTask() 
	{
		return new Task<LoginResult>()
		{
			@Override 
			protected LoginResult call() throws Exception
			{
				return login();
			}
		};
	}
}
