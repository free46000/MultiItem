package com.freelib.multiitem.demo.bean;

/**
 * 消息实体
 * Created by free46000 on 2017/3/20.
 */
public class MessageBean {
    private String message;
    private String sender;

    public MessageBean(String message, String sender) {
        this.message = message;
        this.sender = sender;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getSender() {
        return sender;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }
}
