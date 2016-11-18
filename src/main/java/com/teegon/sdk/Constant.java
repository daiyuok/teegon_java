package com.teegon.sdk;

/**
 * Created by uu on 16-11-16.
 */
public class Constant {


    public static final String SubMessageCmd = "submessage";    //订阅队列消息

    public static final String UnSubMessageCmd = "unsubmessage";  //取消订阅

    public static final String MessageAckCmd = "messageack";    //消息确认

    public static final String HeartBeatCmd = "heartbeat";     //心跳

    public static final String MessageNotifyCmd = "messagenotify"; //订阅成功后的数据通知

    public static final String WriteMessageCmd = "writemessage";  //往队列写消息


    public static final String SIGN_METHOD = "sign_method";
    public static final String SIGN = "sign";
    public static final String DEFAULT_CHARSET = "utf-8";
    public static final String METHOD_GET = "GET";
    public static final String METHOD_POST = "POST";
    public static final String SEPARATOR = "&";
    

}
