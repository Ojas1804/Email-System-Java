package application.model.services;

import java.util.List;

import javax.mail.Folder;

import javafx.concurrent.Service;
import javafx.concurrent.Task;

// Update email when a new message has arrived or a message is deleted
@SuppressWarnings("rawtypes")
public class UpdateFolderService extends Service
{
	private List<Folder> folderList;   // contains the list of all the folderts from all the meail ids
	
	
	public UpdateFolderService(List<Folder> folderList)
	{
		this.folderList = folderList;
	}


	@Override
	protected Task createTask()
	{
		return new Task()
		{

			@Override
			protected Object call() throws Exception
			{
				while(true)
				{
					try
					{
						Thread.sleep(5000);   // to check after every 5 secs
						for(Folder folder : folderList)
						{
							if(folder.getType() != Folder.HOLDS_FOLDERS && folder.isOpen())
							{
								folder.getMessageCount();   // this will trigger an event if message has been added or deleted
							}
						}
					}
					catch (Exception e)
					{
						e.printStackTrace();
					}
				}
			}
			
		};
	}
	
}
