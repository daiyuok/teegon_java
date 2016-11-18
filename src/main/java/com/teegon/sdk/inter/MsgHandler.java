package com.teegon.sdk.inter;

import com.teegon.sdk.pojo.ResponseMsg;
import jp.a840.websocket.WebSocket;
import jp.a840.websocket.exception.WebSocketException;

public interface MsgHandler {

    public void onOpen(WebSocket socket);

    public void onMessage(WebSocket socket, ResponseMsg responseMsg);

    public void onError(WebSocket socket, WebSocketException e);

    public void onClose(WebSocket socket);

}
