package com.teegon.sdk.pojo;

/**
 * Created by uu on 16-11-17.
 */
public class Command {

    private String command;

    private String request_id;

    private Object body;


    public Command(String command, String request_id, Object body) {
        this.command = command;
        this.request_id = request_id;
        this.body = body;
    }

    public String getCommand() {
        return command;
    }

    public void setCommand(String command) {
        this.command = command;
    }

    public String getRequest_id() {
        return request_id;
    }

    public void setRequest_id(String request_id) {
        this.request_id = request_id;
    }

    public Object getBody() {
        return body;
    }

    public void setBody(Object body) {
        this.body = body;
    }


    @Override
    public String toString() {
        if (body != null) {
            return "{\"command\":\"" + this.command + "\",\"request_id\":\"" + request_id + "\",\"body\":" + body.toString() + "}";
        } else {
            return "{\"command\":\"" + this.command + "\",\"request_id\":\"" + request_id + "}";
        }
    }

    
}
