package jp.nephy.nephy.gui;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.ListView;
import javafx.scene.control.Tab;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import twitter4j.ResponseList;
import twitter4j.Status;
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

	private boolean isDetailShown = false;

	@FXML private VBox window_user_vbox_detail;
	@FXML private ImageView window_user_imageview_user_icon;
	@FXML private Text window_user_text_user_name;
	@FXML private Text window_user_text_screen_name;
	@FXML private Text window_user_text_location;
	@FXML private Text window_user_text_bio;
	@FXML private Hyperlink window_user_hyperlink_link;
	@FXML private Button window_user_button_show_detail;
	@FXML private Tab window_user_tab_likes;

	@FXML private ListView<Status> window_user_listview_tweets;
	@FXML private ListView<Status> window_user_listview_likes;
	@FXML private ListView<User> window_user_listview_following;
	@FXML private ListView<User> window_user_listview_follower;

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
			if(!isDetailShown) {
				Utils.showPane(window_user_vbox_detail);
				window_user_button_show_detail.setText("△");
				isDetailShown = true;
			} else {
				Utils.hidePane(window_user_vbox_detail);
				window_user_button_show_detail.setText("▽");
				isDetailShown = false;
			}
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

		window_user_listview_tweets.setCellFactory(listview -> new CellStatus(primaryStage, twitter));
		ObservableList<Status> list = createUserStatus();
		window_user_listview_tweets.setItems(list);

	}

	private ObservableList<Status> createUserStatus() {
		ResponseList<Status> userTimeline = null;
		try {
			userTimeline = twitter.getUserTimeline(userId);
		} catch (TwitterException e) {
			e.printStackTrace();
			return null;
		}
		return Utils.toObservableList(userTimeline);
	}

	private ObservableList<Status> createUserLikes() {
		ResponseList<Status> userLikes = null;
		try {
			userLikes = twitter.getFavorites(userId);
		} catch (TwitterException e) {
			e.printStackTrace();
			return null;
		}
		return Utils.toObservableList(userLikes);
	}

	private ObservableList<User> createFollowing() {
		ResponseList<User> userFollowing = null;
		try {
			userFollowing = twitter.getFriendsList(userId, 1);
		} catch (TwitterException e) {
			e.printStackTrace();
			return null;
		}
		return Utils.toObservableList(userFollowing);
	}
}
