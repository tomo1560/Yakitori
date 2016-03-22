package jp.nephy.nephy.gui;

import java.util.concurrent.ConcurrentHashMap;

import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;
import twitter4j.Status;
import twitter4j.User;

public class CellStatusController {
	@FXML private GridPane gridpane_status;
	@FXML private Text text_user_name;
	@FXML private Text text_status_content;
	@FXML private ImageView imageview_user_icon;

	public void update(Status item, ListView<Status> list, ConcurrentHashMap<Long, Image> cache){
		text_user_name.wrappingWidthProperty().bind(list.widthProperty().subtract(30));
		text_status_content.wrappingWidthProperty().bind(list.widthProperty().subtract(30));

		text_user_name.setText(item.getUser().getName() + " @" + item.getUser().getScreenName());
		text_status_content.setText(item.getText());
		imageview_user_icon.setImage(imageCache(cache, item.getUser()));
	}

	private Image imageCache(ConcurrentHashMap<Long, Image> cache, User user) {
		Image image;
		if(cache.containsKey(user.getId())){
			image = cache.get(user.getId());
		} else {
			image = new Image(user.getProfileImageURL());
			cache.put(user.getId(), image);
		}
		return image;

	}
}
