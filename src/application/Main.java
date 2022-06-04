package application;
	
import application.view.ViewFactory;
import javafx.application.Application;
import javafx.stage.Stage;


public class Main extends Application
{
	@Override
	public void start(Stage stage) throws Exception
	{
////		FXMLLoader loader = new FXMLLoader(this.getClass().getResource("view/first.fxml"));  
////		Parent parent = (Parent) loader.load();  //     ----> Alternate method of doing FXMLLoader.load thing
//		Parent parent = FXMLLoader.load(getClass().getResource("view/MainWindow.fxml"));
//		Scene scene = new Scene(parent);
//		scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
//		stage.setScene(scene);
//		stage.show();
		
		ViewFactory viewFactory = new ViewFactory(new EmailManager());
		viewFactory.showLoginWindow();
		viewFactory.updateScene();
	}
	
	
	public static void main(String[] args)
	{
		launch(args);
	}
}

// communication between different controller classes using SIngleton class
// 					Singleton.getInstance().getSomethingelse().doThat()

// communication between different controller classes using dependencies    ------> Learnt this

// controller view launcher archietechture.    -----> Learnt this

// How dependencies work in this code:
/* 
 * We have created 3 classes, PrimaryController (an abstract class), EmailManager, and ViewFactory
 * All the controller classes will extend abstract class PrimaryController.
 * PrimaryController contains instances of EmailManager, ViewFactory and the name of fxml file using it.
 * ViewFactory contains instance of EmailManager. It sends the action to EMailManager which calls the correct
 * function.
 * 
 * 
 * JavaFx Concurrency solutions and Service class
 * 
 * JavaMailAPI or javax.mail
 * New methods for gmail Oauth2 and XOauth2. Study them but used only for gmail
 * SMTP4, POP3, IMAP, MAIM
 */
