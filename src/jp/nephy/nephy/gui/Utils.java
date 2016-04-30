package jp.nephy.nephy.gui;

import java.util.concurrent.Callable;
import java.util.concurrent.FutureTask;
import java.util.concurrent.RunnableFuture;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Control;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Region;
import twitter4j.ResponseList;

public class Utils {
	static void showControl(Control control) {
		control.setMaxSize(Region.USE_COMPUTED_SIZE, Region.USE_COMPUTED_SIZE);
		control.setMinSize(Region.USE_COMPUTED_SIZE, Region.USE_COMPUTED_SIZE);
		control.setVisible(true);
	}

	static void hideControl(Control control) {
		control.setVisible(false);
		control.setMaxSize(0, 0);
		control.setMinSize(0, 0);
	}

	static void showPane(Pane pane) {
		pane.setMaxSize(Region.USE_COMPUTED_SIZE, Region.USE_COMPUTED_SIZE);
		pane.setMinSize(Region.USE_COMPUTED_SIZE, Region.USE_COMPUTED_SIZE);
		pane.setVisible(true);
	}

	static void hidePane(Pane pane) {
		pane.setVisible(false);
		pane.setMaxSize(0, 0);
		pane.setMinSize(0, 0);
	}

	static <T> ObservableList<T> toObservableList(ResponseList<T> responce) {
		ObservableList<T> list = FXCollections.observableArrayList();
		for(T item: responce) {
			list.add(item);
		}
		return list;
	}

	public static <V> V getFromApplicationThread(Callable<V> callable) throws Exception{
		if (Platform.isFxApplicationThread()) {
			return callable.call();
		} else {
			RunnableFuture<V> future = new FutureTask<V>(callable);
			Platform.runLater(future);
			return future.get();
		}
	}
}
