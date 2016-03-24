package jp.nephy.nephy.gui;

import java.io.IOException;
import java.util.concurrent.ConcurrentHashMap;

import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.ListCell;
import javafx.scene.image.Image;
import twitter4j.Status;

public class CellStatus extends ListCell<Status>{
	static ConcurrentHashMap<Long, Image> imageCache;

	private CellStatusController controller;

	public static ConcurrentHashMap<Long, Image> getImageCache() {
		return imageCache;
	}

	public static void setImageCache(long id, Image image) {
		imageCache.put(id, image);
	}

	public CellStatus(){
		imageCache = new ConcurrentHashMap<>();
		initComponent();
	}

	private void initComponent() {
	}

	@Override
	protected void updateItem(Status item, boolean empty){
		super.updateItem(item, empty);
		if(empty || item == null){
			setGraphic(null);
			setText(null);
			controller = null;
			return;
		}

		if(controller == null || getGraphic() == null){
			FXMLLoader loader = new FXMLLoader(getClass().getResource("CellStatus.fxml"));
			Node node = null;
			try{
				node = loader.load();
			} catch(IOException e){
				e.printStackTrace();;
			}
			controller = loader.getController();
			setGraphic(node);
			controller.update(item, getListView(), imageCache);
		} else {
			controller.update(item, getListView(), imageCache);
		}

	}
}
