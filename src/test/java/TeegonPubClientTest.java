import com.teegon.sdk.inter.MsgHandler;
import com.teegon.sdk.pojo.ResponseMsg;
import com.teegon.sdk.TeegonClient;
import jp.a840.websocket.WebSocket;
import jp.a840.websocket.exception.WebSocketException;
import org.junit.Test;

import java.io.UnsupportedEncodingException;


public class TeegonPubClientTest {
    String url = "http://192.168.10.73:8001/message/websocket";
    String appkey = "6w8m6yb";
    String secret = "xGRwfwWJaNeFsDhT8Fty";

    Boolean block_model = true;

    @Test
    public void websocketConnect() {
        final TeegonClient teegonClient = new TeegonClient(url, appkey, secret, block_model);
        teegonClient.setTeegonMsgHandler(new MsgHandler() {
            @Override
            public void onOpen(WebSocket socket) {
                System.out.println("---> open");
                pub(teegonClient);
            }

            @Override
            public void onMessage(WebSocket socket, ResponseMsg resMsg) {
                System.out.println("---> receive msg:" + resMsg);
            }

            @Override
            public void onError(WebSocket socket, WebSocketException e) {
                e.printStackTrace();
                System.out.println("---> error:" + e);
            }

            @Override
            public void onClose(WebSocket socket) {
                System.out.println("---> close");
            }
        });
        teegonClient.executeNotify();
    }

    public void pub(TeegonClient teegonClient) {
        for (int i = 0; i <= 0; i++) {
            teegonClient.publish("default", "", "A");
        }
    }
}
