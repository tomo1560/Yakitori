package jp.nephy.nephy.gui;

import java.awt.Desktop;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextInputDialog;
import javafx.stage.Stage;
import jp.nephy.twitter.AuthTwitter;


public class WindowWelcomeController implements Initializable{
	Stage primaryStage;
	AuthTwitter twitter = new AuthTwitter();

	@FXML private Label label_welcome;
	@FXML private Button button_log_in;

	public void setStage(Stage stage){
		primaryStage = stage;
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		button_log_in.setOnAction(e -> {
			TextInputDialog dialog = new TextInputDialog();
			dialog.setTitle("Authorization - Nephy");
			dialog.setHeaderText("");
			dialog.setContentText("Pin:");

			twitter.setTwitterInstance();
			browseAuthPage(twitter);

			Optional<String> result = dialog.showAndWait();
			if(result.isPresent()) {
				twitter.setAccessToken(result.get());
				FXMLLoader loader = new FXMLLoader(getClass().getResource("WindowMain.fxml"));
				Scene scene = null;
				try {
					scene = new Scene((Parent)loader.load());
				} catch (IOException er) {
					er.printStackTrace();
				}
				WindowMainController controller = loader.getController();
				controller.setTwitter(twitter.getTwitter());
				controller.setStage(primaryStage);

				Stage stage = new Stage();
				stage.setScene(scene);
				stage.setTitle("Nephy");
				stage.show();
				((Node)e.getSource()).getScene().getWindow().hide();
			}
		});
	}

	public void browseAuthPage(AuthTwitter twitter){
		Desktop desktop = Desktop.getDesktop();
		try {
			desktop.browse(new URI(twitter.getAuthURL()));
		} catch (IOException | URISyntaxException e) {
			e.printStackTrace();
		}
	}
}
