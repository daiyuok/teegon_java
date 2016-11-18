import com.teegon.sdk.inter.MsgHandler;
import com.teegon.sdk.pojo.ResponseMsg;
import com.teegon.sdk.TeegonClient;
import jp.a840.websocket.WebSocket;
import jp.a840.websocket.exception.WebSocketException;
import org.junit.Test;


public class TeegonConsumeClientTest {
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
                consume(teegonClient);
            }

            @Override
            public void onMessage(WebSocket socket, ResponseMsg resMsg) {
                System.out.println("---> receive msg:" + resMsg);
            }

            @Override
            public void onError(WebSocket socket, WebSocketException e) {
            }

            @Override
            public void onClose(WebSocket socket) {
                System.out.println("---> close");
            }
        });
        teegonClient.executeNotify();
    }

    public void consume(TeegonClient teegonClient) {
        teegonClient.consume("default");
    }

}
