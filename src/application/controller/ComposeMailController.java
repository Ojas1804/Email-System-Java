package application.controller;

import java.io.File;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import application.EmailManager;
import application.model.EmailAccount;
import application.model.EmailSendingResult;
import application.model.services.SendEmailService;
import application.view.ViewFactory;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.web.HTMLEditor;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

public class ComposeMailController extends PrimaryController implements Initializable
{
	@FXML
    private Label errorLabel;

    @FXML
    private HTMLEditor htmlEditor;

    @FXML
    private TextField recipientTextField;

    @FXML
    private TextField subjectTextField;
    
    @FXML
    private ChoiceBox<EmailAccount> emailAccountChoice;
    
    private List<File> attachments = new ArrayList<File>();
    
    
    public ComposeMailController(EmailManager emailManager, ViewFactory viewFactory, String fxmlFileName)
    {
		super(emailManager, viewFactory, fxmlFileName);
		emailAccountChoice = new ChoiceBox<EmailAccount>();
	}

    @FXML
    void sendButtonAction()
    {
    	SendEmailService sendEmailService = new SendEmailService(emailAccountChoice.getValue(), 
    			subjectTextField.getText(), 
    			recipientTextField.getText(), 
    			htmlEditor.getHtmlText(),
    			attachments);
    	sendEmailService.start();
    	sendEmailService.setOnSucceeded(e -> {
    		EmailSendingResult emailSendingResult = sendEmailService.getValue();
    		switch(emailSendingResult)
    		{
    			case SUCCESS:
    				Stage stage = (Stage) recipientTextField.getScene().getWindow();
    				viewFactory.closeStage(stage);
    				break;
    				
    			case FAILED_BY_PROVIDER:
    				errorLabel.setText("Failed by Provider!");
    				break;
    				
    			case FAILED_BY_UNEXPECTED_ERROR:
    				errorLabel.setText("Unexpected Error!!");
    				break;
    				
				default:
					break;
    		}
    	});
    }
    
    
    @FXML
    void attachButtonAction()
    {
    	FileChooser fileChooser = new FileChooser();
    	File selectedFile = fileChooser.showOpenDialog(null);
    	if(selectedFile != null)
    	{
    		attachments.add(selectedFile);
    	}
    }
    

	@Override
	public void initialize(URL loc, ResourceBundle resources)
	{
		emailAccountChoice.setItems(emailManager.getAddedEmailAccounts());
		emailAccountChoice.setValue(emailManager.getAddedEmailAccounts().get(0));
	}
}
