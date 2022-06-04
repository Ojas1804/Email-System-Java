package application.view.controller;

import application.EmailManager;
import application.view.ViewFactory;
import javafx.fxml.FXML;
import javafx.scene.control.TableView;
import javafx.scene.control.TreeView;
import javafx.scene.web.WebView;

public class MainWindowController extends PrimaryController
{

    public MainWindowController(EmailManager emailManager, ViewFactory viewFactory, String fxmlFileName)
    {
		super(emailManager, viewFactory, fxmlFileName);
	}

	@FXML
    private WebView emailWebView;

    @FXML
    private TableView<?> emailsTableView;

    @FXML
    private TreeView<?> emailsTreeView;

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
}

