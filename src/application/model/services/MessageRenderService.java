package application.model.services;

import java.io.IOException;

import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.internet.MimeBodyPart;
import application.model.EmailMessage;
import javafx.concurrent.Service;
import javafx.concurrent.Task;
import javafx.scene.web.WebEngine;

@SuppressWarnings("rawtypes")
public class MessageRenderService extends Service
{
	private EmailMessage email;
	private WebEngine webEngine; // can be accessed through webview to display messages
	private StringBuffer stringBuffer;
	
	
	
	@SuppressWarnings("unchecked")
	public MessageRenderService(WebEngine webEngine)
	{
		this.webEngine = webEngine;
		this.stringBuffer = new StringBuffer();
		this.setOnSucceeded(e ->
		{
			displayMail();
		});
	}
	
	
	public void setEmail(EmailMessage email)
	{
		this.email = email;
	}
	
	
	private void displayMail()
	{
		webEngine.loadContent(stringBuffer.toString());
	}


	@Override
	protected Task createTask()
	{
		return new Task()
		{
			@Override
			protected Object call() throws Exception
			{
				try
				{
					loadMessage();
				}
				catch(Exception e)
				{
					e.printStackTrace();
				}
				return null;
			}
		};
	}
	
	
	private void loadMessage() throws MessagingException, IOException 
	{
		stringBuffer.setLength(0);    // clears string buffer
		Message message = email.getMessage();
		
		String contentType = message.getContentType();
		if(isSimpleType(contentType))  stringBuffer.append(message.getContent().toString());
		else if(isMultipartType(contentType))
		{
			Multipart multipart = (Multipart) message.getContent();
			email.setNumberOfAttachmentLoaded(countAttachments(multipart));
			loadMultipart(multipart, stringBuffer);
		}
	}
	
	
	private void loadMultipart(Multipart multipart, StringBuffer stringBuffer) throws MessagingException, IOException
	{
		for(int i = multipart.getCount() - 1; i >= 0; i--)
		{
			BodyPart bodyPart = multipart.getBodyPart(i);
			String bodyPartContentType = bodyPart.getContentType();
			
			if(isSimpleType(bodyPartContentType)) stringBuffer.append(bodyPart.getContent().toString());
			else if(isMultipartType(bodyPartContentType))
			{
				Multipart multipart2 = (Multipart) bodyPart.getContent();
				loadMultipart(multipart2, stringBuffer);
			}
			
			// handling attachments
			else if(!isTextPlain(bodyPartContentType))
			{
				MimeBodyPart mimeBodyPart = (MimeBodyPart) bodyPart;
				email.addAttachment(mimeBodyPart);
			}
		}
	}
	
	
	private boolean isTextPlain(String contentType)
	{
		return (contentType.contains("TEXT/PLAIN") || (contentType.contains("TEXT/plain")));
	}
	
	
	private boolean isSimpleType(String contentType)
	{
		if(contentType.contains("TEXT/HTML") ||  contentType.contains("text") || 
				contentType.contains("TEXT/html")) return true;
		return false;
	}
	
	
	private boolean isMultipartType(String contentType)
	{
		if(contentType.contains("multipart") || contentType.contains("multipart/mixed")) return true;
		return false;
	}
	
	
	private int countAttachments(Multipart multipart) throws MessagingException
	{
		int count = 0;
		for(int i = multipart.getCount() - 1; i >= 0; i--)
		{
			BodyPart bodyPart = multipart.getBodyPart(i);
			String bodyPartContentType = bodyPart.getContentType();
			if(isSimpleType(bodyPartContentType)) continue;
			else if(isMultipartType(bodyPartContentType)) continue;
			else if(!isTextPlain(bodyPartContentType)) count++;
		}
		return count;
	}
}
