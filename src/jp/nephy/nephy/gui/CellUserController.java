package jp.nephy.nephy.gui;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ListView;
import javafx.scene.image.ImageView;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import twitter4j.Twitter;
import twitter4j.User;

public class CellUserController {
	private Twitter twitter;
	private ImageCache cache;
	private Stage primaryStage;

	@FXML ImageView cell_user_imageview_icon;
	@FXML Text cell_user_text_name;
	@FXML Text cell_user_text_bio;

	public void setFields(Stage stage, Twitter twitter, ImageCache cache) {
		primaryStage = stage;
		this.twitter = twitter;
		this.cache = cache;
	}

	public void update(User item, ListView<User> list) {
		cell_user_text_name.wrappingWidthProperty().bind(list.widthProperty().subtract(30));
		cell_user_text_bio.wrappingWidthProperty().bind(list.widthProperty().subtract(30));

		cell_user_text_name.setText(item.getName() + " @" + item.getScreenName());
		cell_user_text_bio.setText(item.getDescription());
		cell_user_imageview_icon.setImage(cache.imageCache(item));
		cell_user_imageview_icon.setOnMouseClicked(e -> {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("fxml/WindowUser.fxml"));
			Scene scene = null;
			try {
				scene = new Scene((Parent)loader.load());
			} catch (Exception e1) {
				e1.printStackTrace();
			}
			WindowUserController controller = loader.getController();
			controller.setStage(primaryStage);
			controller.setScene(scene);
			controller.setUserId(item.getId());
			controller.setTwitter(twitter);
			controller.setImageCache(cache);
			controller.reInitialize();

			primaryStage.setScene(scene);
			primaryStage.show();
		});
	}
}
