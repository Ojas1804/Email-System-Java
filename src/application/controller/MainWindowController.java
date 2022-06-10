package application.controller;

import java.net.URL;
import java.util.Date;
import java.util.ResourceBundle;

import application.EmailManager;
import application.model.EmailMessage;
import application.model.ExtendedTreeItem;
import application.model.OptimizedSize;
import application.model.services.MessageRenderService;
import application.view.ViewFactory;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.TreeView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.web.WebView;
import javafx.util.Callback;

public class MainWindowController extends PrimaryController implements Initializable
{

    public MainWindowController(EmailManager emailManager, ViewFactory viewFactory, String fxmlFileName)
    {
		super(emailManager, viewFactory, fxmlFileName);
	}

    @FXML
    private WebView emailWebView;

    @FXML
    private TableView<EmailMessage> emailsTableView;

    @FXML
    private TreeView<String> emailsTreeView;

    @FXML
    private TableColumn<EmailMessage, String> recipientColumn;

    @FXML
    private TableColumn<EmailMessage, String> senderColumn;

    @FXML
    private TableColumn<EmailMessage, OptimizedSize> sizeColumn;

    @FXML
    private TableColumn<EmailMessage, String> subjectColumn;

    @FXML
    private TableColumn<EmailMessage, Date> dateColumn;
    
    private MenuItem deleteMailMenuItem = new MenuItem("Delete");
    private MenuItem markUnreadMenuItem = new MenuItem("Mark as Unread");
    private MenuItem showDetailedEmail = new MenuItem("View Details");
    
    private MessageRenderService messageRenderService;

    
    @FXML
    void optionClicked()
    {
    	viewFactory.showOptionsWindow();
    }
    
    
    @FXML
    void addAccountClicked()
    {
    	viewFactory.showLoginWindow();
    }
    
    
    @FXML
    void composeMailAction()
    {
    	viewFactory.showComposeMailWindow();
    }


	@Override
	public void initialize(URL arg0, ResourceBundle arg1) 
	{
		setUpEmailsTreeView();
		setUpEmailsTableView();
		setUpFolderSelection(); // used upon folder selection
		setUpBoldRows();        // for unread mails
		setUpMessageRenderService();
		setUpMailSelection();
		setUpContextMenus();
	}


	private void setUpEmailsTreeView()
	{
		emailsTreeView.setRoot(emailManager.getRootFolder());
		emailsTreeView.setShowRoot(false);
	}


	private void setUpEmailsTableView()
	{
		senderColumn.setCellValueFactory((new PropertyValueFactory<EmailMessage, String>("sender")));
		subjectColumn.setCellValueFactory((new PropertyValueFactory<EmailMessage, String>("subject")));
		sizeColumn.setCellValueFactory((new PropertyValueFactory<EmailMessage, OptimizedSize>("size")));
		dateColumn.setCellValueFactory((new PropertyValueFactory<EmailMessage, Date>("date")));
		recipientColumn.setCellValueFactory((new PropertyValueFactory<EmailMessage, String>("recipient")));
		
		emailsTableView.setContextMenu(new ContextMenu(markUnreadMenuItem, deleteMailMenuItem, showDetailedEmail));
	}
	
	
	private void setUpFolderSelection()
	{
		emailsTreeView.setOnMouseClicked(e -> {
			ExtendedTreeItem<String> item = (ExtendedTreeItem<String>) emailsTreeView.getSelectionModel().getSelectedItem();
			if(item != null)
			{
				emailManager.setSelectedFolder(item);
				emailsTableView.setItems(item.getEmails());
			}
		});
	}


	private void setUpBoldRows()
	{
		emailsTableView.setRowFactory(new Callback<TableView<EmailMessage>, TableRow<EmailMessage>>()
		{	
			@Override
			public TableRow<EmailMessage> call(TableView<EmailMessage> arg0)
			{
				return new TableRow<EmailMessage>() {
					@Override
					protected void updateItem(EmailMessage item, boolean empty)
					{
						super.updateItem(item, empty);
						if(item != null)
						{
							if(!item.isRead())
							{
								setStyle("-fx-font-weight: bold");
							}
							else
							{
								setStyle("");
							}
						}
					}
				};
			}
		});
	}


	private void setUpMessageRenderService()
	{
		emailWebView.getEngine().loadContent("");
		messageRenderService = new MessageRenderService(emailWebView.getEngine());
	}


	private void setUpMailSelection()
	{
		emailsTableView.setOnMouseClicked(e -> {
			EmailMessage email = emailsTableView.getSelectionModel().getSelectedItem();
			if(email != null)
			{
				emailWebView.getEngine().loadContent("");
				emailManager.setSelectedEmail(email);
				if(!email.isRead()) emailManager.setRead(); // update seen to true if unseen
				messageRenderService.setEmail(email);
				messageRenderService.restart();
				try
				{
					Thread.sleep(1000); // so as to stop printing same message multiple times.
				} 
				catch (InterruptedException e1)
				{
					e1.printStackTrace();
				}
			}
		});
	}


	private void setUpContextMenus()
	{
		markUnreadMenuItem.setOnAction(e -> {
			emailManager.setUnread();
		});
		
		deleteMailMenuItem.setOnAction(e -> {
			emailManager.deleteSelectedMail();
			emailWebView.getEngine().loadContent("");
		});
		
		showDetailedEmail.setOnAction(e -> {
			viewFactory.showDetailedEmailWindow();
		});
	}
}