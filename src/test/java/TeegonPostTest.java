import com.teegon.sdk.util.WebUtils;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import com.teegon.sdk.TeegonClient;

public class TeegonPostTest {


    public static void main(String[] args) {

        String url = "http://apigateway.shopex.cn/router";
        String appkey = "";
        String secret = "";
        String method = "shopex.queue.read";

        TeegonClient teegonClient = new TeegonClient(url, appkey, secret, Boolean.FALSE);
        Map<String, String> appParams = new HashMap<String, String>();
        appParams.put("method", method);
        appParams.put("topic", "orders");
        appParams.put("num", "1");
        try {
            String apiResult = teegonClient.doPost(appParams);
            System.out.println(apiResult);
        } catch (IOException e) {
            e.printStackTrace();
        }


    }

}
