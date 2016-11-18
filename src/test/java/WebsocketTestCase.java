/**
 * Created by uu on 16-11-15.
 */

import jp.a840.websocket.WebSocket;
import jp.a840.websocket.WebSockets;
import jp.a840.websocket.exception.WebSocketException;
import jp.a840.websocket.frame.Frame;
import jp.a840.websocket.handler.WebSocketHandler;

import java.io.IOException;

public class WebsocketTestCase {


    public static void main(String args[]) {
        WebSocket webSocket = null;
        try {
            webSocket = WebSockets.create("ws://192.168.10.73:8001/message/websocket?app_key=6w8m6yb&method=platform%2Fnotify&sign=59B98C169E536F5FBDF071F625444AB3&sign_time=1479372579", new WebSocketHandler() {
                public void onOpen(WebSocket socket) {
                    System.out.println("=========onOpen=======");
                    try {
                        String s = "app_key=6w8m6yb&body=eyJjb21tYW5kIjoic3VibWVzc2FnZSIsInJlcXVlc3RfaWQiOiIiLCJib2R5Ijp7Imdyb3VwIjoiIiwidG9waWMiOiJkZWZhdWx0In19&method=submessage&sign=3C02DA03BC4C9BF8D8326FA258E35261&sign_time=1479372579";
                        socket.send(s.getBytes());
                    } catch (WebSocketException e) {
                        e.printStackTrace();
                    }
                }

                public void onMessage(WebSocket socket, Frame frame) {
                    System.out.println("=========onMessage=======");
                    String msg = new String(frame.getContents().array(), 0, frame.getContents().array().length);
                    System.out.println(msg);
                }

                public void onError(WebSocket socket, WebSocketException e) {
                    System.out.println("=========onError=======");
                }

                public void onClose(WebSocket socket) {
                    System.out.println("=========onClose=======");
                }
            }, null);

            webSocket.setBlockingMode(true);

        } catch (WebSocketException e) {
            e.printStackTrace();
        }
        if (webSocket != null) {
            try {
                webSocket.connect();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (WebSocketException e) {
                e.printStackTrace();
            }
        }

    }
}
