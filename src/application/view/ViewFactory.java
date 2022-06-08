package application.view;

import java.util.LinkedList;

import application.EmailManager;
import application.controller.ComposeMailController;
import application.controller.DetailedEmailWindowController;
import application.controller.LoginWindowController;
import application.controller.MainWindowController;
import application.controller.OptionsWindowController;
import application.controller.PrimaryController;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class ViewFactory
{
	private EmailManager emailManager;
	private LinkedList<Stage> activeStageList;
	private boolean mainViewInitialized = false;
	
	private Themes theme = Themes.GITHUBLIGHT;
	private FontSize fontSize = FontSize.MEDIUM;

	
	
	public ViewFactory(EmailManager emailManager)
	{
		super();
		this.setEmailManager(emailManager);
		this.activeStageList = new LinkedList<>();
	}
	
	
	
	public boolean isMainViewInitialized()
	{
		return mainViewInitialized;
	}


	
	public EmailManager getEmailManager()
	{
		return emailManager;
	}

	

	public void setEmailManager(EmailManager emailManager)
	{
		this.emailManager = emailManager;
	}
	
	
	// Main functions
	public void initializeStage(PrimaryController controller)
	{
		FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource(controller.getFxmlFileName()));
		fxmlLoader.setController(controller);
		
		Parent parent;
		try
		{
			parent = fxmlLoader.load();
		}
		catch(Exception e)
		{
			e.printStackTrace();
			return;
		}
		
		Scene scene = new Scene(parent);
		Stage stage = new Stage();
		stage.setScene(scene);
		stage.show();
		activeStageList.add(stage);
	}
	
	
	// show login window
	public void showLoginWindow()
	{
		PrimaryController controller = new LoginWindowController(emailManager, this, "Login.fxml");
		initializeStage(controller);
	}
	
	
	// show main window
	public void showMainWindow()
	{
		updateScene();
		PrimaryController controller = new MainWindowController(emailManager, this, "MainWindow.fxml");
		initializeStage(controller);
		this.mainViewInitialized = true;
	}
	
	
	// show options window
	public void showOptionsWindow()
	{
		PrimaryController controller = new OptionsWindowController(emailManager, this, "Options.fxml");
		initializeStage(controller);
	}
	
	
	// show compose mail window
	public void showComposeMailWindow()
	{
		PrimaryController controller = new ComposeMailController(emailManager, this, "ComposeMail.fxml");
		initializeStage(controller);
	}
	
	
	// show detailed email window
	public void showDetailedEmailWindow()
	{
		PrimaryController controller = new DetailedEmailWindowController(emailManager, this, "DetailedEmailWindow.fxml");
		initializeStage(controller);
	}
	
	
	// closing window
	public void closeStage(Stage stage)
	{
		stage.close();
		activeStageList.remove(stage);
	}


	//--------------------------------------------OPTION HANDLING---------------------------------------------//
//	private Themes theme = Themes.DARK;
//	private FontSize fontSize = FontSize.MEDIUM;
	
	
	public Themes getTheme()
	{
		return theme;
	}


	public void setTheme(Themes theme)
	{
		this.theme = theme;
	}


	public FontSize getFontSize()
	{
		return fontSize;
	}
	
	
	public void setFontSize(FontSize fontSize)
	{
		this.fontSize = fontSize;
	}
	
	
	
	// ------------------------------UPDATE SCENE------------------------------------------------ //
	public void updateScene()   // update scene style when updated in options window
	{
		for(Stage stage : activeStageList)
		{
			Scene scene = stage.getScene();
			// Update CSS for this scene
			scene.getStylesheets().clear();   // remove the default or alraedy existing imported stylesheets
			scene.getStylesheets().add(getClass().getResource(Themes.getPath(theme)).toExternalForm());
			scene.getStylesheets().add(getClass().getResource(FontSize.getPath(fontSize)).toExternalForm());
		}
	}
}
