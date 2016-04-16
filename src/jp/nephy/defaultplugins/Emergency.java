package jp.nephy.defaultplugins;

import java.net.URI;
import javax.websocket.ClientEndpoint;
import javax.websocket.CloseReason;
import javax.websocket.ContainerProvider;
import javax.websocket.OnClose;
import javax.websocket.OnError;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.WebSocketContainer;

@ClientEndpoint
public class Emergency {
	static boolean connected = false;

	@OnError
	public void onError(Session session, Throwable cause) {
		// エラー
		System.out.println("error : " + session.getId() + ", " + cause.getMessage());
	}

	@OnMessage
	public void onMessage(String msg, Session session) {
		// 文字列を受け取る
		System.out.println("onMessage: " + msg);
	} 

	@OnClose
	public void onClose(Session session, CloseReason closeReason) {
		// 接続が閉じられた
		System.out.println("closed");
		connected = false;
	}

	@OnOpen
	public void onOpen(Session session) {
		// 接続した
		System.out.println("opened");
	}

	public static void connect() {
		WebSocketContainer container = ContainerProvider.getWebSocketContainer();
		Class<?> c = JustInWeatherInformation.class;

		// 接続
		try (Session session = container.connectToServer(
				c, URI.create("ws://api.nephy.jp/v1/ws/emergency"))) {
			connected = true;

			while (connected) {
				// 接続中の処理

			}
		} catch (Exception e) {
			System.err.println(e);
		}
	}
	public static void main(String[] args) {
		connect();
	}
}
