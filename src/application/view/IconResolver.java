package application.view;

import javafx.scene.Node;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class IconResolver
{
	public Node getFolderIcon(String folderName)
	{
		String lowerCaseFolderName = folderName.toLowerCase();
		ImageView iconImage;
		try
		{
			if(lowerCaseFolderName.contains("@")) 
				iconImage = new ImageView(new Image(getClass().getResourceAsStream("Images/Icons/email.png")));
			else if(lowerCaseFolderName.contains("drafts")) 
				iconImage = new ImageView(new Image(getClass().getResourceAsStream("Images/Icons/draft.png")));
			else if(lowerCaseFolderName.contains("spam")) 
				iconImage = new ImageView(new Image(getClass().getResourceAsStream("Images/Icons/spam.png")));
			else if(lowerCaseFolderName.contains("inbox")) 
				iconImage = new ImageView(new Image(getClass().getResourceAsStream("Images/Icons/inbox.png")));
			else if(lowerCaseFolderName.contains("sent")) 
				iconImage = new ImageView(new Image(getClass().getResourceAsStream("Images/Icons/sent2.png")));
			else if(lowerCaseFolderName.contains("trash")) 
				iconImage = new ImageView(new Image(getClass().getResourceAsStream("Images/Icons/trash.png")));
			else if(lowerCaseFolderName.contains("bin")) 
				iconImage = new ImageView(new Image(getClass().getResourceAsStream("Images/Icons/trash.png")));
			else
				iconImage = new ImageView(new Image(getClass().getResourceAsStream("Images/Icons/folder.png")));;
		}
		catch (Exception e)
		{
			e.printStackTrace();
			return null;
		}
		iconImage.setFitHeight(16);
		iconImage.setFitWidth(16);
		return iconImage;
	}
}
