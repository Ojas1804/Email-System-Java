package application;
	
import java.util.ArrayList;
import java.util.List;

import application.controller.persistence.PersistenceAccess;
import application.controller.persistence.ValidAccount;
import application.model.EmailAccount;
import application.model.services.LoginService;
import application.view.ViewFactory;
import javafx.application.Application;
import javafx.stage.Stage;


public class Main extends Application
{
	private EmailManager emailManager = new EmailManager();
	private PersistenceAccess persistenceAccess = new PersistenceAccess();
	
	@Override
	public void start(Stage stage) throws Exception
	{	
		ViewFactory viewFactory = new ViewFactory(emailManager);
		viewFactory.updateScene();
		List<ValidAccount> validAccounts = persistenceAccess.loadFromPersistence();
		
		if(validAccounts.size() > 0)
		{
			viewFactory.showMainWindow();
			for(ValidAccount validAccount : validAccounts)
			{
				EmailAccount emailAccount = new EmailAccount(validAccount.getEmailId(), validAccount.getPassword());
				LoginService loginService = new LoginService(emailAccount, emailManager);
				loginService.start();
			}
		}
		else
		{
			viewFactory.showLoginWindow();
		}
	}
	
	
	@Override
	public void stop() throws Exception // to save email id and password of entered email account so that there is 
										// no need to enter it in future
	{
		List<ValidAccount> validEmailAccounts = new ArrayList<ValidAccount>();
		for(EmailAccount emailAccount : emailManager.getAddedEmailAccounts())
		{
			validEmailAccounts.add(new ValidAccount(emailAccount.getEmailId(), emailAccount.getPassword()));
		}
		persistenceAccess.saveToPersistence(validEmailAccounts);
	}
	
	
	public static void main(String[] args)
	{
		launch(args);
	}
}