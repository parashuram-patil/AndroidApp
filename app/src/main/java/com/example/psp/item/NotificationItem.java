package com.example.psp.item;

public class NotificationItem {
    String title;
    String body;

    public NotificationItem(String title, String body) {
        this.title = title;
        this.body = body;
    }

    public String getTitle() {
        return title;
    }

    public String getBody() {
        return body;
    }
}
