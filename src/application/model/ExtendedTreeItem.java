package application.model;

import javax.mail.Flags;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.TreeItem;

// ExtendedTreeItem has been built to show unread messages which was not possible in TreeItem.
@SuppressWarnings("hiding")
public class ExtendedTreeItem<String> extends TreeItem<String>
{
	private String name;
	private ObservableList<EmailMessage> emails;
	private int unreadMessageCount;

	
	public ExtendedTreeItem(String name)
	{
		super(name);
		this.setName(name);
		this.emails = FXCollections.observableArrayList();
	}
	
	
	public ObservableList<EmailMessage> getEmails()
	{
		return this.emails;
	}


	public String getName()
	{
		return name;
	}


	public void setName(String name)
	{
		this.name = name;
	}
	
	
	@SuppressWarnings("unchecked")
	private void updateName()
	{
		if(this.unreadMessageCount > 0)
		{
			this.setValue((String) (name + "( " + unreadMessageCount + " )"));
		}
		else
		{
			this.setValue(name);
		}
	}
	
	
	
	public EmailMessage fetchEmailMessage(Message message) throws MessagingException
	{
		EmailMessage email = new EmailMessage(message.getSubject(), 
				message.getFrom()[0].toString(),
				message.getRecipients(MimeMessage.RecipientType.TO)[0].toString(),
				message.getSize(),
				message.getSentDate(), 
				message.getFlags().contains(Flags.Flag.SEEN),
				message);
		
		if(!email.isRead())
		{
			incrementMessageCount();
		}
		return email;
	}
	
	
	public void incrementMessageCount()
	{
		this.unreadMessageCount++;
		updateName();
	}
	
	
	public void decrementMessageCount()
	{
		this.unreadMessageCount--;
		updateName();
	}
	
	
	public void addMail(Message message) throws MessagingException
	{
		EmailMessage email = fetchEmailMessage(message);
		emails.add(email);
	}


	public void addMailToTop(Message message) throws MessagingException
	{
		EmailMessage email = fetchEmailMessage(message);
		emails.add(0, email);
	}
}
