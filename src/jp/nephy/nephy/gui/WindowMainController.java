package jp.nephy.nephy.gui;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextArea;
import javafx.stage.Stage;
import twitter4j.ResponseList;
import twitter4j.Status;
import twitter4j.Twitter;
import twitter4j.TwitterException;

public class WindowMainController implements Initializable {
	private Twitter twitter = null;

	private Stage primaryStage;

	@FXML private ListView<Status> listview_hometimeline;
	@FXML private TextArea textarea_tweet_content;
	@FXML private Button button_tweet_post;
	@FXML private MenuItem menuitem_file_close, menuitem_help_about,menuitem_tools_settings;


	public void setTwitter(Twitter twitter){
		this.twitter = twitter;
		listview_hometimeline.setCellFactory(listView -> new CellStatus());
		ObservableList<Status> list = createStatus();
		listview_hometimeline.setItems(list);
	}

	public void setStage(Stage stage) {
		primaryStage = stage;
	}

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
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
			} finally {
				button_tweet_post.setText("Tweet");
				button_tweet_post.setDisable(false);
			}
		});

		menuitem_file_close.setOnAction(e -> {
			Platform.exit();
		});
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
