package application.controller;

import application.EmailManager;
import application.view.ViewFactory;

public abstract class PrimaryController
{
	protected EmailManager emailManager;
	protected ViewFactory viewFactory;
	private String fxmlFileName;
	
	
	public PrimaryController(EmailManager emailManager, ViewFactory viewFactory, String fxmlFileName) {
		super();
		this.emailManager = emailManager;
		this.viewFactory = viewFactory;
		this.fxmlFileName = fxmlFileName;
	}
	
	
	public EmailManager getEmailManager()
	{
		return emailManager;
	}


	public void setEmailManager(EmailManager emailManager)
	{
		this.emailManager = emailManager;
	}


	public ViewFactory getViewFactory() {
		return viewFactory;
	}


	public void setViewFactory(ViewFactory viewFactory)
	{
		this.viewFactory = viewFactory;
	}


	public String getFxmlFileName()
	{
		return fxmlFileName;
	}


	public void setFxmlFileName(String fxmlFileName)
	{
		this.fxmlFileName = fxmlFileName;
	}
}
