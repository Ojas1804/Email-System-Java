module email_system
{
	requires javafx.controls;
	requires javafx.graphics;
	requires javafx.fxml;
	requires javafx.web;
	requires javafx.base;
	requires activation;
	requires java.mail;
	
	opens application to javafx.graphics, javafx.fxml;
	opens application.view.controller;
}
