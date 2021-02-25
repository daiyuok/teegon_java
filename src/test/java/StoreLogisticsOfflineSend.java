import com.teegon.sdk.TeegonClient;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class StoreLogisticsOfflineSend {


    public static void main(String[] args) {

        String url = "http://apigateway.shopex.cn/router";
        String appkey = "xx";
        String secret = "xx";
        String method = "store.logistics.offline.send";

        TeegonClient teegonClient = new TeegonClient(url, appkey, secret, Boolean.FALSE);
        Map<String, String> appParams = new HashMap<String, String>();
        appParams.put("method", method);
        appParams.put("tid", "4767633381843150624A");
        appParams.put("company_code", "OTHER");
        appParams.put("company_name", "上海客户自提");
        appParams.put("logistics_no", "310131872833");
        appParams.put("node_id", "1665176836_1031190436");
        appParams.put("request_type", "sync");

        try {
            String apiResult = teegonClient.doPost(appParams);
            System.out.println(apiResult);
        } catch (IOException e) {
            e.printStackTrace();
        }


    }

}
