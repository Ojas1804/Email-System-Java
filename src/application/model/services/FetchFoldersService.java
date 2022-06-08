package application.model.services;

import java.util.List;

import javax.mail.Folder;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Store;
import javax.mail.event.MessageCountEvent;
import javax.mail.event.MessageCountListener;

import application.model.ExtendedTreeItem;
import application.view.IconResolver;
import javafx.concurrent.Service;
import javafx.concurrent.Task;

public class FetchFoldersService extends Service<Void>
{
	private Store store;   // to store email folders and emails
	private ExtendedTreeItem<String> root;
	private List<Folder> folderList;
	private IconResolver iconResolver = new IconResolver();
	
	
	
	public FetchFoldersService(Store store, ExtendedTreeItem<String> root, List<Folder> folderList)
	{
		super();
		this.store = store;
		this.root = root;
		this.folderList = folderList;
	}
	
	
	
	private void fetchFolders() throws MessagingException
	{
		Folder[] folders = store.getDefaultFolder().list();
		handleFolders(folders);
	}
	


	private void handleFolders(Folder[] folders) throws MessagingException
	{
		for(Folder folder : folders)
		{
			folderList.add(folder);
			ExtendedTreeItem<String> treeItem = new ExtendedTreeItem<String>(folder.getName());
			treeItem.setGraphic(iconResolver.getFolderIcon(folder.getName()));
			root.getChildren().add(treeItem);
			root.setExpanded(true);
			fetchEmails(folder, treeItem);
			
			addMessageListenerToFolder(folder, treeItem);
			
			if(folder.getType() == Folder.HOLDS_FOLDERS)
			{
				Folder[] subfolders = folder.list();
				handleFolders(subfolders, treeItem);
			}
		}
	}
	
	
	
	private void addMessageListenerToFolder(Folder folder, ExtendedTreeItem<String> treeItem)
	{
		folder.addMessageCountListener(new MessageCountListener() {
			
			@Override
			public void messagesRemoved(MessageCountEvent e)
			{
				System.out.println("Message deteled event" + e);
			}
			
			@Override
			public void messagesAdded(MessageCountEvent e) 
			{
//				System.out.println("Message Added: " + e);
				for(int i = 0; i < e.getMessages().length; i++)
				{
					System.out.println("Message added event" + e);
					try
					{
						Message message = folder.getMessage(folder.getMessageCount() - i);
						treeItem.addMailToTop(message);
					}
					catch (MessagingException e1)
					{
						e1.printStackTrace();
					}
				}
			}
		});
	}



	private void fetchEmails(Folder folder, ExtendedTreeItem<String> treeItem) 
	{
		@SuppressWarnings("rawtypes")
		Service fetchEmailsService = new Service()
		{

			@Override
			protected Task createTask()
			{
				return new Task() {

					@Override
					protected Object call() throws Exception
					{
						if(folder.getType() != Folder.HOLDS_FOLDERS)
						{
							folder.open(Folder.READ_WRITE);
							int folderSize = folder.getMessageCount();
							
							for(int i = folderSize; i > 0; i--)
							{
								treeItem.addMail(folder.getMessage(i));
							}
						}
						return null;
					}
					
				};
			}
			
		};
		fetchEmailsService.start();
		
	}
	


	private void handleFolders(Folder[] folders, ExtendedTreeItem<String> folderRoot)
	{
		for(Folder folder : folders)
		{
			ExtendedTreeItem<String> treeItem = new ExtendedTreeItem<String>(folder.getName());
			root.getChildren().add(treeItem);
			root.setExpanded(true);
		}
	}


	
	@Override
	protected Task<Void> createTask() 
	{
		
		return new Task<Void>()
		{
			@Override
			protected Void call() throws Exception
			{
				fetchFolders();
				return null;
			}
			
		};
	}
}
