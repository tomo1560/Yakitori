package jp.nephy.twitter;

import javafx.collections.ObservableList;
import javafx.concurrent.Service;
import javafx.concurrent.Task;
import javafx.scene.control.ListView;
import jp.nephy.nephy.gui.Utils;
import twitter4j.StallWarning;
import twitter4j.Status;
import twitter4j.StatusDeletionNotice;
import twitter4j.StatusListener;
import twitter4j.TwitterStream;
import twitter4j.TwitterStreamFactory;
import twitter4j.conf.Configuration;
import twitter4j.conf.ConfigurationBuilder;

public class Streaming {
	public static TwitterStream userStreaming(ObservableList<Status> list, AuthTwitter authTwitter, ListView<Status> listView) {
		ConfigurationBuilder builder = new ConfigurationBuilder();
		builder.setOAuthConsumerKey(authTwitter.getConsumerKey());
		builder.setOAuthConsumerSecret(authTwitter.getConsumerSecret());
		builder.setOAuthAccessToken(authTwitter.getAccessToken().getToken());
		builder.setOAuthAccessTokenSecret(authTwitter.getAccessToken().getTokenSecret());
		Configuration conf = builder.build();
		TwitterStream stream = new TwitterStreamFactory(conf).getInstance();
		StatusListener listener = new StatusListener() {
			@Override
			public void onStatus(Status status) {
				new Service<Void>() {
					@Override
					protected Task<Void> createTask() {
						return new Task<Void>() {
							@Override
							protected Void call() throws Exception {
								Utils.getFromApplicationThread(() -> {
									list.add(0, status);
									listView.setItems(list);
									return null;
								});
								return null;
							}
						};
					}
				}.start();
			}

			@Override
			public void onException(Exception e) {
				e.printStackTrace();
				//ロガーに回す
			}

			@Override
			public void onDeletionNotice(StatusDeletionNotice arg0) {
				//削除されたツイートをタイムラインから削除する操作 (設定で無効にできるようにする)
			}

			@Override
			public void onScrubGeo(long arg0, long arg1) {
				//位置情報の一括削除; 該当ツイートから位置情報を削除する操作(設定で無効にできるようにする)
			}

			@Override
			public void onStallWarning(StallWarning arg0) {
				//UserStreamに関する警告
			}

			@Override
			public void onTrackLimitationNotice(int arg0) {
				//Trackのリミットについての警告
			}
		};
		stream.addListener(listener);
		return stream;
	}
}
