package application;

import java.util.ArrayList;
import java.util.List;

import javax.mail.Flags;
import javax.mail.Folder;

import application.model.EmailAccount;
import application.model.EmailMessage;
import application.model.ExtendedTreeItem;
import application.model.services.FetchFoldersService;
import application.model.services.UpdateFolderService;
import application.view.IconResolver;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class EmailManager
{
	private UpdateFolderService updateFolderService;
	private ExtendedTreeItem<String> rootFolder = new ExtendedTreeItem<String>("");
	private List<Folder> folderList = new ArrayList<>();
	private IconResolver iconResolver = new IconResolver();
	
	private EmailMessage selectedEmail;
	private ExtendedTreeItem<String> selectedFolder;
	
	private ObservableList<EmailAccount> addedEmailAccounts = FXCollections.observableArrayList();
	
	
	// constructor
	public EmailManager()
	{
		updateFolderService = new UpdateFolderService(folderList);
		updateFolderService.start();
	}

	
	// getters and setters
	public void setRootFolder(ExtendedTreeItem<String> rootFolder)
	{
		this.rootFolder = rootFolder;
	}
	

	public ExtendedTreeItem<String> getRootFolder()
	{
		return rootFolder;
	}
	
	
	public List<Folder> getFolderList()
	{
		return this.folderList;
	}


	public EmailMessage getSelectedEmail()
	{
		return selectedEmail;
	}


	public void setSelectedEmail(EmailMessage selectedEmail)
	{
		this.selectedEmail = selectedEmail;
	}


	public ExtendedTreeItem<String> getSelectedFolder()
	{
		return selectedFolder;
	}


	public void setSelectedFolder(ExtendedTreeItem<String> selectedFolder)
	{
		this.selectedFolder = selectedFolder;
	}


	public ObservableList<EmailAccount> getAddedEmailAccounts()
	{
		return addedEmailAccounts;
	}


	// Main methods
	public void addEmailAccount(EmailAccount emailAccount)
	{
		addedEmailAccounts.add(emailAccount);
		ExtendedTreeItem<String> treeItem = new ExtendedTreeItem<String>(emailAccount.getEmailId());
		treeItem.setGraphic(iconResolver.getFolderIcon(emailAccount.getEmailId()));
		FetchFoldersService fetchFolderService = new FetchFoldersService(emailAccount.getMailStore(), treeItem,
																		 folderList);
//		treeItem.setExpanded(true);
		fetchFolderService.start();
		rootFolder.getChildren().add(treeItem);
	}
	
	
	public void setRead()
	{
		try
		{
			selectedEmail.setIsRead(true);
			selectedEmail.getMessage().setFlag(Flags.Flag.SEEN, true);
			selectedFolder.decrementMessageCount();
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}
	
	
	public void setUnread()
	{
		try
		{
			selectedEmail.setIsRead(false);
			selectedEmail.getMessage().setFlag(Flags.Flag.SEEN, false);
			selectedFolder.incrementMessageCount();
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}
	
	
	public void deleteSelectedMail()
	{
		try
		{
			selectedEmail.getMessage().setFlag(Flags.Flag.DELETED, true);
			selectedFolder.getEmails().remove(selectedEmail);
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}
}
