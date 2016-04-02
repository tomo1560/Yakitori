package jp.nephy.nephy.gui;

import java.util.concurrent.ConcurrentHashMap;

import javafx.scene.image.Image;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.User;

public class ImageCache {
	private ConcurrentHashMap<Long, Image> cache;

	public ImageCache() {
		cache = new ConcurrentHashMap<>();
	}

	public Image imageCache(User user) {
		Image image;
		if(cache.containsKey(user.getId())){
			image = cache.get(user.getId());
		} else {
			image = new Image(user.getProfileImageURL());
			cache.put(user.getId(), image);
		}
		return image;
	}

	public Image imageCache(Twitter twitter, long userId) {
		User user;
		try {
			user = twitter.showUser(userId);
		} catch (TwitterException e) {
			e.printStackTrace();
			return null;
		}
		return imageCache(user);
	}
}
