package jp.nephy.nephy.gui;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ListView;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import twitter4j.Status;
import twitter4j.Twitter;

public class CellStatusController {
	private Twitter twitter;
	private ImageCache cache;
	private Stage primaryStage;

	@FXML private GridPane gridpane_status;
	@FXML private Text text_user_name;
	@FXML private Text text_status_content;
	@FXML private ImageView imageview_user_icon;

	public void setFields(Stage stage, Twitter twitter, ImageCache cache) {
		primaryStage = stage;
		this.twitter = twitter;
		this.cache = cache;
	}

	@FXML
	public void initialize() {
	}

	public void update(Status item, ListView<Status> list){
		text_user_name.wrappingWidthProperty().bind(list.widthProperty().subtract(30));
		text_status_content.wrappingWidthProperty().bind(list.widthProperty().subtract(30));

		text_user_name.setText(item.getUser().getName() + " @" + item.getUser().getScreenName());
		text_status_content.setText(item.getText());
		imageview_user_icon.setImage(cache.imageCache(item.getUser()));
		imageview_user_icon.setOnMouseClicked(e -> {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("WindowUser.fxml"));
			Scene scene = null;
			try {
				scene = new Scene((Parent)loader.load());
			} catch (Exception e1) {
				e1.printStackTrace();
			}
			WindowUserController controller = loader.getController();
			controller.setStage(primaryStage);
			controller.setScene(scene);
			controller.setUserId(item.getUser().getId());
			controller.setTwitter(twitter);
			controller.setImageCache(cache);
			controller.reInitialize();

			primaryStage.setScene(scene);
			primaryStage.show();
		});
	}
}
