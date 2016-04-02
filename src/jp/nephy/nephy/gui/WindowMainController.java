package jp.nephy.nephy.gui;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextArea;
import javafx.stage.Stage;
import jp.nephy.twitter.AuthTwitter;
import jp.nephy.twitter.Streaming;
import twitter4j.ResponseList;
import twitter4j.Status;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterStream;

public class WindowMainController {
	private AuthTwitter authTwitter = null;
	private Twitter twitter = null;
	private TwitterStream stream;

	private Stage primaryStage;

	@FXML private ListView<Status> listview_hometimeline;
	@FXML private TextArea textarea_tweet_content;
	@FXML private Button button_tweet_post;
	@FXML private MenuItem menuitem_file_close, menuitem_help_about,menuitem_tools_settings;


	public void setTwitter(AuthTwitter twitter){
		this.authTwitter = twitter;
		this.twitter = twitter.getTwitter();
	}

	public void setStage(Stage stage) {
		primaryStage = stage;
	}

	@FXML
	private void initialize() {
		button_tweet_post.setOnAction(e -> {
			button_tweet_post.setDisable(true);
			button_tweet_post.setText("Posting...");
			try {
				twitter.updateStatus(textarea_tweet_content.getText());
				textarea_tweet_content.setText("");
				textarea_tweet_content.setPromptText("What are you doing?");
			} catch (TwitterException er) {
				er.printStackTrace();
				Alert alert = new Alert(AlertType.ERROR);
				alert.setTitle("ツイートに失敗 - Nephy ");
				alert.setHeaderText("");
				alert.setContentText("ツイートに失敗しました。エラーコード:" + er.getErrorCode());
				alert.show();
			} finally {
				button_tweet_post.setText("Tweet");
				button_tweet_post.setDisable(false);
			}
		});

		menuitem_file_close.setOnAction(e -> {
			handleExitRequest();
		});
		menuitem_help_about.setOnAction(e -> {
			Alert alert = new Alert(AlertType.INFORMATION);
			alert.setTitle("About - Nephy");
			alert.setHeaderText("Nephy");
			alert.setContentText("");
			alert.show();
		});
	}

	public void reInitialize() {
		primaryStage.setOnCloseRequest(e -> {
			handleExitRequest();
		});
		listview_hometimeline.setCellFactory(listView -> new CellStatus(primaryStage, twitter));
		ObservableList<Status> list = createStatus();
		listview_hometimeline.setItems(list);
		stream = Streaming.userStreaming(list, authTwitter, listview_hometimeline);
		stream.user();
	}

	private void handleExitRequest() {
		stream.shutdown();
		Platform.exit();
	}

	private ObservableList<Status> createStatus(){
		ResponseList<Status> timeline = null;
		ObservableList<Status> list = FXCollections.observableArrayList();
		try {
			timeline = twitter.getHomeTimeline();
		} catch (TwitterException e) {
			e.printStackTrace();
		}
		for(Status item : timeline) {
			list.add(item);
		}
		return list;
	}
}
