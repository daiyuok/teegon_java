package com.teegon.sdk.pojo;

/**
 * Created by uu on 16-11-17.
 * 订阅数据的结构
 */
public class SubMessage {

    private String group;


    private String topic;

    public SubMessage(String topic, String group) {
        this.topic = topic;
        this.group = group;
    }


    public String getGroup() {
        return group;
    }

    public void setGroup(String group) {
        this.group = group;
    }

    public String getTopic() {
        return topic;
    }

    public void setTopic(String topic) {
        this.topic = topic;
    }


    @Override
    public String toString() {
        return "{\"group\":\"" + this.group + "\",\"topic\":\"" + this.topic + "\"}";
    }

}
