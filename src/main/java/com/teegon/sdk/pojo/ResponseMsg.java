package com.teegon.sdk.pojo;


public class ResponseMsg {
    private String ecode;
    private String command;
    private String result;

    public String getEcode() {
        return ecode;
    }

    public void setEcode(String ecode) {
        this.ecode = ecode;
    }

    public String getCommand() {
        return command;
    }

    public void setCommand(String command) {
        this.command = command;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    @Override
    public String toString() {
        return "ResponseMsg{" +
                "ecode='" + ecode + '\'' +
                ", command='" + command + '\'' +
                ", result=" + result + '\'' +
                '}';
    }
}
