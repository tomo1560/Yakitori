package jp.nephy.nephy.gui;

import java.io.IOException;

import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.ListCell;
import javafx.stage.Stage;
import twitter4j.Twitter;
import twitter4j.User;

public class CellUser extends ListCell<User> {
	private Stage primaryStage;
	private ImageCache cache;

	private CellUserController controller;
	private Twitter twitter;

	public CellUser(Stage stage, Twitter twitter){
		cache = new ImageCache();
		this.twitter = twitter;
		primaryStage = stage;
	}

	@Override
	protected void updateItem(User item, boolean empty) {
		super.updateItem(item, empty);
		if(empty || item == null){
			setGraphic(null);
			setText(null);
			controller = null;
			return;
		}
		if(controller == null || getGraphic() == null){
			FXMLLoader loader = new FXMLLoader(getClass().getResource("CellUser.fxml"));
			Node node = null;
			try{
				node = loader.load();
			} catch(IOException e){
				e.printStackTrace();;
			}
			controller = loader.getController();
			controller.setFields(primaryStage, twitter, cache);
			setGraphic(node);
			controller.update(item, getListView());
		} else {
			controller.setFields(primaryStage, twitter, cache);
			controller.update(item, getListView());
		}
	}

}
