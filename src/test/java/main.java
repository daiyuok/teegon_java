import com.teegon.sdk.util.Base64;
import com.teegon.sdk.util.URLParser;
import com.teegon.sdk.util.WebUtils;
import sun.misc.BASE64Encoder;
import sun.misc.BASE64Decoder;
import org.apache.log4j.Logger;


/**
 * Created by uu on 16-11-16.
 */
public class main {
    private static Logger logger = Logger.getLogger(main.class);

    public static void main(String args[]) {

        logger.info("========");

        String url = "http://192.168.10.73:8001/message/websocket";

        URLParser urlParser = new URLParser(url);
        System.out.println(urlParser.getPath());
        System.out.println(urlParser.getWsUrl(""));

        System.out.println(WebUtils.encode("/message/websocket", "utf-8"));

        System.out.println(WebUtils.encode("app_key=6w8m6yb", "utf-8"));

        System.out.println(System.currentTimeMillis() / 1000);

        String s0 = "eyJjb21tYW5kIjoid3JpdGVtZXNzYWdlIiwicmVxdWVzdF9pZCI6IjEiLCJib2R5I";

        System.out.println(Base64.decodeBase64(s0));


        String s1 = "eyJjb21tYW5kIjoid3JpdGVtZXNzYWdlIiwicmVxdWVzdF9pZCI6IjEiLCJib2R5Ijp7InRvcGljIjoiZGVmYXVsdCIsImRhdGEiOiJBIiwia2V5IjoiIn19";

        System.out.println(Base64.decodeBase64(s1));

//        String s1 = "{\"command\":\"writemessage\",\"request_id\":\"1\",\"body\":{\"topic\":\"default\",\"data\":\"A\",\"key\":\"\"}}";
//
//        System.out.println(new BASE64Encoder().encode(s1.getBytes()));

    }
}
