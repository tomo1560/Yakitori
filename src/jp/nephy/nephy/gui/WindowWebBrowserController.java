package jp.nephy.nephy.gui;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.paint.Color;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import javafx.stage.Stage;

public class WindowWebBrowserController extends Application{
	public static void main(String[] args) {
		Application.launch(WindowWebBrowserController.class, args);
	}

	@Override
	public void start(Stage primaryStage) {
		primaryStage.setTitle("Browser - Nephy");
		Group root = new Group();
		Scene scene = new Scene(root, 800, 600);

		WebEngine engine = new WebEngine("https://google.co.jp/");
		WebView view = new WebView(engine);
		root.getChildren().add(view);
		
		primaryStage.setScene(scene);
		//primaryStage.setVisible(true);
	}
}
