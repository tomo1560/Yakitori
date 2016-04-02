package jp.nephy.nephy.gui;

import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Hyperlink;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.User;


public class WindowUserController {
	private long userId;
	private User user = null;
	private Stage primaryStage;
	private Scene scene;
	private Twitter twitter = null;
	private ImageCache cache;

	@FXML VBox window_user_vbox_detail;
	@FXML ImageView window_user_imageview_user_icon;
	@FXML Text window_user_text_user_name;
	@FXML Text window_user_text_screen_name;
	@FXML Text window_user_text_location;
	@FXML Text window_user_text_bio;
	@FXML Hyperlink window_user_hyperlink_link;
	@FXML Button window_user_button_show_detail;

	public void setStage(Stage stage) {
		primaryStage = stage;
	}

	public void setScene(Scene scene) {
		this.scene = scene;
	}

	public void setUserId(long userId) {
		this.userId = userId;
	}

	public void setTwitter(Twitter twitter) {
		this.twitter = twitter;
	}

	public void setImageCache(ImageCache cache) {
		this.cache = cache;
	}

	@FXML
	public void initialize() {
		Utils.hidePane(window_user_vbox_detail);
		window_user_button_show_detail.setOnAction(e -> {
			Utils.showPane(window_user_vbox_detail);
		});
	}

	public void reInitialize() {
		primaryStage.setOnCloseRequest(e -> {
			scene.getWindow().hide();
		});
		try {
			user = twitter.showUser(userId);
		} catch (TwitterException e) {
			e.printStackTrace();
			return;
		}
		double width = window_user_vbox_detail.getWidth() - 20;

		window_user_text_user_name.setText(user.getName());
		window_user_text_user_name.setWrappingWidth(width);
		window_user_text_screen_name.setText("@" + user.getScreenName());
		window_user_text_screen_name.setWrappingWidth(width);
		window_user_text_location.setText(user.getLocation());
		window_user_text_location.setWrappingWidth(width);
		window_user_hyperlink_link.setText(user.getURL());
		window_user_text_bio.setText(user.getDescription());
		window_user_text_bio.setWrappingWidth(width);
		window_user_imageview_user_icon.setImage(cache.imageCache(user));
	}

}
