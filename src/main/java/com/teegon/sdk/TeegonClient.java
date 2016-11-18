package com.teegon.sdk;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.teegon.sdk.inter.MsgHandler;
import com.teegon.sdk.pojo.Command;
import com.teegon.sdk.pojo.ResponseMsg;
import com.teegon.sdk.pojo.SubMessage;
import com.teegon.sdk.pojo.WriteMessage;
import com.teegon.sdk.util.Base64;
import com.teegon.sdk.util.SignTools;
import com.teegon.sdk.util.URLParser;
import com.teegon.sdk.util.WebUtils;
import jp.a840.websocket.WebSocket;
import jp.a840.websocket.WebSockets;
import jp.a840.websocket.exception.WebSocketException;
import jp.a840.websocket.frame.Frame;
import jp.a840.websocket.handler.WebSocketHandler;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.ByteBuffer;
import java.util.HashMap;
import java.util.Map;
import java.util.Date;

/**
 * <p>teegon 客户端</p>
 */
public class TeegonClient {
    private Map<String, String> sysParams;//api请求系统级参数
    private String urlStr;//API请求URL
    private String appkey;//应用appkey
    private String secret;//应用密钥
    private WebSocket webSocket;
    private MsgHandler msgHandler;

    private String method = "platform/notify";

    private URLParser urlParser;

    private Boolean block_model;

    public TeegonClient(String url, String appkey, String secret, Boolean block_model) {
        this.urlStr = url;
        this.appkey = appkey;
        this.secret = secret;
        urlParser = new URLParser(urlStr);
        this.block_model = block_model;
        this.initSysParams();
    }

    /**
     * 初始化组装系统级参数
     */
    private void initSysParams() {
        sysParams = new HashMap<String, String>();
        sysParams.put("app_key", appkey);//appkey
        sysParams.put("method", method);//secret
        sysParams.put("sign_time", String.valueOf(new Date().getTime() / 1000));
    }

    /**
     * 组装所有请求参数
     */
    public Map<String, String> assembleParams(Map<String, String> headers, Map<String, String> appParams, String methodType, String urlPath) {
        Map<String, String> allParams = new HashMap<String, String>();
        allParams.putAll(sysParams);
        if (appParams != null && allParams.size() > 0) {
            allParams.putAll(appParams);
        }

        String sign = "";
        if (methodType.equals(Constant.METHOD_GET)) {
            sign = sign(headers, allParams, null, Constant.METHOD_GET, urlPath);
        } else if (methodType.equals(Constant.METHOD_POST)) {
            sign = sign(headers, null, allParams, Constant.METHOD_POST, urlPath);
        }
        allParams.put(Constant.SIGN, sign);
        return allParams;
    }

    /**
     * 执行签名
     *
     * @param headerParams http头信息
     * @param getParams    get参数
     * @param postParams   post参数
     * @param method       http请求方式
     * @param path         http请求path
     * @return
     */
    private String sign(Map<String, String> headerParams, Map<String, String> getParams, Map<String, String> postParams, String method, String path) {
        //header数据拼接字符串
        String mixHeaderParams = SignTools.mixHeaderParams(headerParams);
        //get数据拼接字符串
        String mixGetParams = SignTools.mixRequestParams(getParams);
        //post数据拼接字符串
        String mixPostParams = SignTools.mixRequestParams(postParams);
        //签名拼接字符串
        String mixAllParams = secret + Constant.SEPARATOR
                + method + Constant.SEPARATOR
                + urlencode(path) + Constant.SEPARATOR
                + urlencode(mixHeaderParams) + Constant.SEPARATOR
                + urlencode(mixGetParams) + Constant.SEPARATOR
                + urlencode(mixPostParams) + Constant.SEPARATOR
                + secret;

//        System.out.println("===========");
//
//        System.out.println(mixAllParams);
//
//        System.out.println("===========");

        //加密签名
        try {
            return SignTools.byte2hex(SignTools.encryptMD5(mixAllParams), true);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    private String urlencode(String str) {
        try {
            return URLEncoder.encode(str, Constant.DEFAULT_CHARSET);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            return "";
        }
    }

    /**
     * 连接websocket
     */
    public WebSocket executeNotify() {
        try {
            String websocketUrl = urlParser.getWsUrl("");
            websocketUrl = websocketUrl + "?" + WebUtils.buildQuery(assembleParams(null, null, Constant.METHOD_GET, urlParser.getWsPath("")), Constant.DEFAULT_CHARSET);

//            System.out.println(websocketUrl);
            webSocket = WebSockets.create(websocketUrl, new TeegonWebSocketHandler(), "char");
            webSocket.setBlockingMode(block_model);
            webSocket.connect();

        } catch (IOException e) {
            System.out.println();
        } catch (WebSocketException e) {
            System.out.println();
        }
        return webSocket;
    }

    public void runHeatBeats() {
        try {
            while (true) {
                webSocket.send(assembleHeatBeatsData().getBytes());
                Thread.sleep(3000);
            }
        } catch (InterruptedException e) {
            System.out.println();
        } catch (WebSocketException e) {
            System.out.println();
        }
    }

    /**
     * 组装心跳信息数据
     */
    public String assembleHeatBeatsData() {
//        System.out.println("======assembleHeatBeatsData=========");
        Command command = new Command(Constant.HeartBeatCmd, "", null);
        return packProtocol(command);
    }


    /**
     * 设置websocket生命周期函数
     */
    public void setTeegonMsgHandler(MsgHandler msgHandler) {
        this.msgHandler = msgHandler;
    }

    /**
     * 开启消息消费
     */
    public void consume(String topic) {
        try {
            webSocket.send(assembleConsumeData(topic).getBytes());
        } catch (WebSocketException e) {
            e.printStackTrace();
        }
    }


    /**
     * 发布消息
     */
    public void publish(String topic, String key, String body) {
        try {
            webSocket.send(assemblePublishData(topic, key, body).getBytes());
        } catch (WebSocketException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }


    /**
     * 组装消费信息数据
     */
    public String assembleConsumeData(String topic) {
        Command command = new Command(Constant.SubMessageCmd, "", new SubMessage(topic, ""));
        return packProtocol(command);
    }

    /**
     * 组装发布消息数据
     */
    public String assemblePublishData(String topic, String key, String body) throws UnsupportedEncodingException {
        Command command = new Command(Constant.WriteMessageCmd, "1", new WriteMessage(topic, key, body));
        return packProtocol(command);
    }


    /**
     * 组装请求参数
     */
    public String packProtocol(Command command) {
//        System.out.println(command.toString());
        HashMap<String, String> vals = new HashMap<String, String>();
        vals.put("method", command.getCommand());
        vals.put("app_key", appkey);
        vals.put("sign_time", String.valueOf(new Date().getTime() / 1000));
        vals.put("body", Base64.encodeBase64(command.toString()));
        vals.put("sign", sign(null, vals, null, "", ""));

//        System.out.println(vals.get("body"));
//
//        System.out.println(vals.get("sign"));

        String data = "";
        try {
            data = WebUtils.buildQuery(vals, Constant.DEFAULT_CHARSET);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return data;
    }

    private class TeegonWebSocketHandler implements WebSocketHandler {
        @Override
        public void onOpen(WebSocket webSocket) {
            if (msgHandler != null) {
//                runHeatBeats();
                msgHandler.onOpen(webSocket);
            }
        }

        @Override
        public void onMessage(WebSocket webSocket, Frame frame) {
            String msg = new String(frame.getContents().array(), 0, frame.getContents().array().length);
            ResponseMsg prismMsg = JSON.parseObject(msg, ResponseMsg.class);
            if (msgHandler != null) {
                msgHandler.onMessage(webSocket, prismMsg);
            }
        }

        @Override
        public void onError(WebSocket webSocket, WebSocketException e) {
            if (msgHandler != null) {
                msgHandler.onError(webSocket, e);
            }
        }

        @Override
        public void onClose(WebSocket webSocket) {
            if (msgHandler != null) {
                msgHandler.onClose(webSocket);
                executeNotify();
            }
        }
    }
}
