package com.example.signupsignin.Model;

public
class Message {
    private String content,sender,receiver;
    public
    Message(){}

    public
    Message(String content, String sender, String receiver) {
        this.content = content;
        this.sender = sender;
        this.receiver = receiver;
    }

    public
    String getContent() {
        return content;
    }

    public
    void setContent(String content) {
        this.content = content;
    }

    public
    String getSender() {
        return sender;
    }

    public
    void setSender(String sender) {
        this.sender = sender;
    }

    public
    String getReceiver() {
        return receiver;
    }

    public
    void setReceiver(String receiver) {
        this.receiver = receiver;
    }
}
