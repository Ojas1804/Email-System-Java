package application.controller.persistence;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;

public class PersistenceAccess
{
	private String VALID_ACCOUNTS_LOCATION = System.getenv("APPDATA") + File.separator +"validAccount.ser";
	private Encoder encoder = new Encoder();
	
	
	public List<ValidAccount> loadFromPersistence()
	{
		List<ValidAccount> emailList =  new ArrayList<ValidAccount>();
		try
		{
			FileInputStream fileInputStream = new FileInputStream(VALID_ACCOUNTS_LOCATION);
			
			try (ObjectInputStream objInputStream = new ObjectInputStream(fileInputStream))
			{
				@SuppressWarnings("unchecked")
				List<ValidAccount> persistedList = (List<ValidAccount>) objInputStream.readObject();
				decodePasswords(persistedList);
				emailList.addAll(persistedList);
			}
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		
		return emailList;
	}


	public void saveToPersistence(List<ValidAccount> emailList)
	{
		try
		{
			File file = new File(VALID_ACCOUNTS_LOCATION);
			FileOutputStream fileOutputStream = new FileOutputStream(file);
			ObjectOutputStream objOutputStream = new ObjectOutputStream(fileOutputStream);
			encodePasswords(emailList);
			objOutputStream.writeObject(emailList);
			objOutputStream.close();
			fileOutputStream.close();
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}
	
	
	private void decodePasswords(List<ValidAccount> persistedList)
	{
		for(ValidAccount validAccount : persistedList)
		{
			String originalPassword = validAccount.getPassword();
			validAccount.setPassword(encoder.decode(originalPassword));
		}
	}
	
	
	private void encodePasswords(List<ValidAccount> persistedList)
	{
		for(ValidAccount validAccount : persistedList)
		{
			String originalPassword = validAccount.getPassword();
			validAccount.setPassword(encoder.encode(originalPassword));
		}
	}
}
