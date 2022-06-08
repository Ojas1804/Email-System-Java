package application.controller.persistence;

import java.io.Serializable;

public class ValidAccount implements Serializable
{
	private static final long serialVersionUID = -806056423125305390L;
	private String emailId;
	private String password;
	
	
	public ValidAccount(String emailId, String password)
	{
		super();
		this.emailId = emailId;
		this.password = password;
	}


	public String getEmailId()
	{
		return emailId;
	}


	public void setEmailId(String emailId)
	{
		this.emailId = emailId;
	}


	public String getPassword()
	{
		return password;
	}


	public void setPassword(String password)
	{
		this.password = password;
	}
	
	
	
}
