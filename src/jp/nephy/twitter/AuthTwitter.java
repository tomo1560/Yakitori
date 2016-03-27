package jp.nephy.twitter;

import java.io.IOException;
import java.util.Properties;

import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;
import twitter4j.auth.AccessToken;
import twitter4j.auth.RequestToken;
import twitter4j.conf.ConfigurationBuilder;

public class AuthTwitter {
	private Twitter twitter;
	private String consumerKey;
	private String consumerSecret;
	private RequestToken rToken;
	private AccessToken aToken;

	public String getConsumerKey() {
		return consumerKey;
	}

	public String getConsumerSecret() {
		return consumerSecret;
	}

	public AccessToken getAccessToken() {
		return aToken;
	}

	public Twitter getTwitter() {
		return twitter;
	}

	public void setTwitterInstanceDebug() {
		ConfigurationBuilder builder = new ConfigurationBuilder();
		builder.setDebugEnabled(true)
			.setOAuthConsumerKey(consumerKey)
			.setOAuthConsumerSecret(consumerSecret)
			.setOAuthAccessToken("**************************************************")
			.setOAuthAccessTokenSecret("******************************************");
		twitter = new TwitterFactory(builder.build()).getInstance();
	}

	public void setTwitterInstance() {
		twitter = TwitterFactory.getSingleton();
		twitter.setOAuthConsumer(consumerKey, consumerSecret);
	}

	public String getAuthURL() {
		String url = null;
		try {
			rToken = twitter.getOAuthRequestToken();
			url = rToken.getAuthenticationURL();
		} catch (TwitterException e) {
			e.printStackTrace();
		}
		return url;
	}

	public boolean setAccessToken(String pin) {
		try {
			aToken = twitter.getOAuthAccessToken(rToken, pin);
		} catch (TwitterException e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}

	public void getConsumerKeys() {
		Properties prop = new Properties();
		try {
			prop.load(getClass().getResourceAsStream("keys.properties"));
		} catch (IOException e) {
			e.printStackTrace();
			return;
		}
		consumerKey = prop.getProperty("Key");
		consumerSecret = prop.getProperty("Secret");
	}
}
