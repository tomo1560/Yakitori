package jp.nephy.nephy.gui;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.web.WebView;
import javafx.stage.Stage;

public class WindowWebBrowserController implements Initializable{
	private Stage primaryStage;

	@FXML private WebView window_web_browser_webview;

	public void setStage (Stage stage) {
		primaryStage = stage;
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		primaryStage.setTitle("Browser - Nephy");
		FXMLLoader loader = new FXMLLoader(getClass().getResource("fxml/WindowWebBrowser.fxml"));
		Scene scene = null;
		try {
			scene = loader.load();
		} catch(IOException e) {
			e.printStackTrace();
		}
		primaryStage.setScene(scene);
		primaryStage.show();
	}
}
