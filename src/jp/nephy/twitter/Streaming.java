package jp.nephy.twitter;

import javafx.collections.ObservableList;
import javafx.scene.control.ListView;
import twitter4j.StallWarning;
import twitter4j.Status;
import twitter4j.StatusDeletionNotice;
import twitter4j.StatusListener;
import twitter4j.TwitterStream;
import twitter4j.TwitterStreamFactory;
import twitter4j.conf.Configuration;
import twitter4j.conf.ConfigurationBuilder;

public class Streaming {
	public static void userStreaming(ObservableList<Status> list, AuthTwitter authTwitter, ListView<Status> listView) {
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
				list.add(0, status);
				listView.setItems(list);
			}

			@Override
			public void onException(Exception e) {
				e.printStackTrace();
			}

			@Override
			public void onDeletionNotice(StatusDeletionNotice arg0) {

			}

			@Override
			public void onScrubGeo(long arg0, long arg1) {

			}

			@Override
			public void onStallWarning(StallWarning arg0) {

			}

			@Override
			public void onTrackLimitationNotice(int arg0) {

			}
		};
		stream.addListener(listener);
		stream.user();
	}
}
