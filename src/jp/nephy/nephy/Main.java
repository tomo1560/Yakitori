package jp.nephy.nephy;

import java.io.IOException;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import jp.nephy.nephy.gui.WindowWelcomeController;

public class Main extends Application {
	@Override
	public void start(Stage primaryStage) {
		FXMLLoader loader = new FXMLLoader(getClass().getResource("gui/fxml/WindowWelcome.fxml"));
		Scene scene = null;
		try {
			scene = new Scene((Parent)loader.load());
		} catch (IOException e) {
			e.printStackTrace();
		}
		scene.getStylesheets().add(getClass().getResource("gui/css/application.css").toExternalForm());

		WindowWelcomeController controller = (WindowWelcomeController)loader.getController();
		controller.setStage(primaryStage);

		primaryStage.setScene(scene);
		primaryStage.show();
	}

	public static void main(String[] args) {
	//	ThreadPlugin.main();
		launch(args);
	}
}
