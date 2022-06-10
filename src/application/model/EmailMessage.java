package application.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.mail.Message;
import javax.mail.internet.MimeBodyPart;

import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;

// Data structure that can be displayed in JavaFx
public class EmailMessage
{
	private SimpleStringProperty subject;
	private SimpleStringProperty sender;
	private SimpleStringProperty recipient;
	private SimpleObjectProperty<OptimizedSize> size;
	private boolean isRead;
	private SimpleObjectProperty<Date> date;
	private Message message;
	private List<MimeBodyPart> attachmentList = new ArrayList<MimeBodyPart>();
	private int numberOfAttachmentLoaded;


	private boolean hasAttachment = false;
	private boolean attachmentLoaded = false;
	
	
	
	public EmailMessage(String subject, String sender, String recipient, int size, 
						Date date, boolean isRead, Message message)
	{
		super();
		this.subject = new SimpleStringProperty(subject);
		this.sender = new SimpleStringProperty(sender);
		this.recipient = new SimpleStringProperty(recipient);
		this.size = new SimpleObjectProperty<OptimizedSize>(new OptimizedSize(size));
		this.isRead = isRead;
		this.date = new SimpleObjectProperty<Date>(date);
		this.message = message;
	}



	public String getSubject()
	{
		return this.subject.get();
	}



	public String getSender()
	{
		return this.sender.get();
	}



	public String getRecipient()
	{
		return this.recipient.get();
	}



	public OptimizedSize getSize()
	{
		return this.size.get();
	}



	public boolean isRead()
	{
		return this.isRead;
	}
	
	
	public void setIsRead(boolean isRead)
	{
		this.isRead = isRead;
	}



	public Date getDate()
	{
		return this.date.get();
	}



	public Message getMessage()
	{
		return this.message;
	}



	public void addAttachment(MimeBodyPart mimeBodyPart)
	{
		hasAttachment = true;
//		attachmentLoaded = true;
		if(attachmentList.size() < this.numberOfAttachmentLoaded) attachmentList.add(mimeBodyPart);
	}



	public boolean isHasAttachment()
	{
		return hasAttachment;
	}
	
	
	public boolean isAttachmentLoaded()
	{
		return attachmentLoaded;
	}
	
	
	public List<MimeBodyPart> getAttachmentList()
	{
		return attachmentList;
	}
	
	
	public void setNumberOfAttachmentLoaded(int num)
	{
		this.numberOfAttachmentLoaded = num;
	}
}
