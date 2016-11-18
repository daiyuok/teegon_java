package com.teegon.sdk.pojo;

/**
 * Created by uu on 16-11-16.
 * 写数据的结构
 */
public class WriteMessage {

    private String Topic;

    private String Data;

    private String Key;

    public WriteMessage() {
        this.Topic = "";
        this.Key = "";
        this.Data = "";
    }

    public WriteMessage(String topic, String key, String data) {
        this.Topic = topic;
        this.Key = key;
        this.Data = data;
    }

    public String getTopic() {
        return Topic;
    }

    public void setTopic(String topic) {
        Topic = topic;
    }

    public String getData() {
        return Data;
    }

    public void setData(String data) {
        Data = data;
    }

    public String getKey() {
        return Key;
    }

    public void setKey(String key) {
        Key = key;
    }


    @Override
    public String toString() {
        return "{\"topic\":\"" + this.Topic + "\",\"data\":\"" + this.Data + "\",\"key\":\"" + this.Key + "\"}";
    }

}
