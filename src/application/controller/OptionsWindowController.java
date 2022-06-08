package application.controller;

import java.net.URL;
import java.util.ResourceBundle;

import application.EmailManager;
import application.view.FontSize;
import application.view.Themes;
import application.view.ViewFactory;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Slider;
import javafx.stage.Stage;
import javafx.util.StringConverter;

public class OptionsWindowController extends PrimaryController implements Initializable     // for initializing default values and styles
{

    public OptionsWindowController(EmailManager emailManager, ViewFactory viewFactory, String fxmlFileName)
    {
		super(emailManager, viewFactory, fxmlFileName);
	}

	@FXML
    private Slider fontResizer;

    @FXML
    private ChoiceBox<Themes> themePicker;

    @FXML
    void applyButtonAction()
    {
    	viewFactory.setTheme(themePicker.getValue());
    	viewFactory.setFontSize(FontSize.values()[(int) fontResizer.getValue()]);
    	viewFactory.updateScene();
    }

    @FXML
    void cancelButtonAction()
    {
    	Stage stage = (Stage) fontResizer.getScene().getWindow();
    	viewFactory.closeStage(stage);
    }

	@Override
	public void initialize(URL location, ResourceBundle resources)
	{
		setThemePicker();
		setFontSize();
	}
	
	
	private void setThemePicker()
	{
		themePicker.setItems(FXCollections.observableArrayList(Themes.values()));
		themePicker.setValue(viewFactory.getTheme());
	}
	
	
	private void setFontSize()
	{
		fontResizer.setMin(0);
		fontResizer.setMax(FontSize.values().length - 1);
		fontResizer.setValue(viewFactory.getFontSize().ordinal());   // ordinal function ???
		fontResizer.setMajorTickUnit(1);
		fontResizer.setMinorTickCount(0);
		fontResizer.setBlockIncrement(1);
		fontResizer.setSnapToTicks(true);
		fontResizer.setShowTickMarks(true);
		fontResizer.setShowTickLabels(true);
		fontResizer.setLabelFormatter(new StringConverter<Double>() 
				{

					@Override
					public Double fromString(String arg0)
					{
						
						return null;
					}

					@Override
					public String toString(Double value)
					{
						int i = value.intValue();
						return FontSize.values()[i].toString();
					}
					
				});
		fontResizer.valueProperty().addListener((obs, oldVal, newVal) -> {
			fontResizer.setValue(newVal.intValue());
		});
	}
}

