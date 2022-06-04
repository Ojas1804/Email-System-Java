package application.view.controller;

import application.EmailManager;
import application.model.EmailAccount;
import application.model.LoginResult;
import application.model.services.LoginService;
import application.view.ViewFactory;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

public class LoginWindowController extends PrimaryController
{

    public LoginWindowController(EmailManager emailManager, ViewFactory viewFactory, String fxmlFileName)
    {
		super(emailManager, viewFactory, fxmlFileName);
	}

    
	@FXML
    private TextField emailID;

    @FXML
    private Label errorLabel;

    @FXML
    private ImageView logo;

    @FXML
    private PasswordField password;

    
    @FXML
    void loginClicked()
    {
    	if(fieldsAreValid())
    	{
    		EmailAccount emailAccount = new EmailAccount(emailID.getText(), password.getText());
    		LoginService loginService = new LoginService(emailAccount, emailManager);
    		loginService.start();
    		
    		loginService.setOnSucceeded(event -> {
    			LoginResult result = loginService.getValue();
        		
        		switch(result) {
        			case SUCCESS:
        				System.out.println("Login Successfull");
        				showMainWindow();
        				return;
        			case FAILED_BY_CREDENTIALS:
        				errorLabel.setText("Wrong password or email-id");
        				return;
        			case FAILED_BY_NETWORK:
        				errorLabel.setText("Network error!");
        				return;
        			case FAILED_BY_UNEXPECTED_ERROR:
        				errorLabel.setText("Unexpected error. Try again Later!");
        				return;
    				default:
    					System.out.println("Login Unsuccessfull");
    					return;
        		}
    		});
    	}
//    	System.out.println("Login Clicked!!");
    }
    
    
    // call this method in case login happens successfully
    private void showMainWindow()
    {
    	viewFactory.showMainWindow();
    	try
    	{
    		viewFactory.closeStage((Stage) errorLabel.getScene().getWindow());   // closing login window
    	}
    	catch(Exception e)
    	{
    		e.printStackTrace();
    		return;
    	}
    }


	private boolean fieldsAreValid()
	{
		if(emailID.getText().isEmpty()) 
		{
			errorLabel.setText("Email ID field can't be empty");
			return false;
		}
		
		else if(password.getText().isEmpty())
		{
			errorLabel.setText("Password field cannot be empty");
			return false;
		}
		
		return true;
	}
}