package application.controller;

import java.awt.Desktop;
import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;

import javax.mail.MessagingException;
import javax.mail.internet.MimeBodyPart;

import application.EmailManager;
import application.model.EmailMessage;
import application.model.services.MessageRenderService;
import application.view.ViewFactory;
import javafx.concurrent.Service;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.web.WebView;

public class DetailedEmailWindowController extends PrimaryController implements Initializable
{

	@FXML
    private HBox hBoxDownloads;

    @FXML
    private Label senderLabel;

    @FXML
    private Label subjectLabel;
    
    @FXML
    private Label attachmentLabel;

    @FXML
    private WebView webView;
    
    private String LOCATION_OF_DOWNLOADS = System.getProperty("user.home") + "\\Downloads\\";
    
    
	public DetailedEmailWindowController(EmailManager emailManager, ViewFactory viewFactory, String fxmlFileName)
	{
		super(emailManager, viewFactory, fxmlFileName);
	}


	@Override
	public void initialize(URL arg0, ResourceBundle arg1)
	{
		EmailMessage email = emailManager.getSelectedEmail();
		subjectLabel.setText(email.getSubject());
		senderLabel.setText(email.getSender());
		try
		{
			loadAttachments(email);
		}
		catch (MessagingException e)
		{
			e.printStackTrace();
		}
		
		MessageRenderService messageRenderService = new MessageRenderService(webView.getEngine());
		messageRenderService.setEmail(email);
		messageRenderService.restart();
	}


	private void loadAttachments(EmailMessage email) throws MessagingException
	{
		if(email.isHasAttachment())
		{
			for(MimeBodyPart mbp : email.getAttachmentList())
			{
				AttachmentButton button = new AttachmentButton(mbp);
				hBoxDownloads.getChildren().add(button);
			}
		}
		else
		{
			attachmentLabel.setText("");
		}
	}
	
	
	// Attachment Button inner class
	private class AttachmentButton extends Button
	{
		private MimeBodyPart mbp;
		private String downloadedFilePath;
		
		
		public AttachmentButton(MimeBodyPart mbp) throws MessagingException
		{
			this.mbp = mbp;
			try
			{
				this.setText(mbp.getFileName());
			} 
			catch (MessagingException e)
			{
				this.setText("file");
				e.printStackTrace();
			}
			this.downloadedFilePath = LOCATION_OF_DOWNLOADS + mbp.getFileName();
			
			this.setOnAction(e -> downloadAttachment());
		}
		

		private void downloadAttachment()
		{
			// Needs to be run in a different thread as it can take time
			colorBlue();
			Service<Void> service = new Service<Void>() {
				@Override
				protected Task<Void> createTask()
				{
					return new Task<Void>()
					{
						@Override
						protected Void call() throws Exception
						{
							mbp.saveFile(downloadedFilePath);
							return null;
						}
						
					};
				}
				
			};
			service.restart();
			service.setOnSucceeded(e -> {
				colorGreen();
				this.setOnAction(e2 -> {
					File file = new File(downloadedFilePath);
					Desktop desktop = Desktop.getDesktop();
					if(file.exists())
					{
						try 
						{
							desktop.open(file);
						}
						catch (Exception e3)
						{
							e3.printStackTrace();
						}
					}
				});
			});
		}
		
		
		private void colorBlue()
		{
			this.setStyle("-fx-background-color: blue");
		}
		
		
		private void colorGreen()
		{
			this.setStyle("-fx-background-color: green");
		}
	}
}
