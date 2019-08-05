package com.example.psp.notimportant;

public class ContentItem {

    final String name;
    final String desc;
    boolean isSection = false;

    public ContentItem(String n) {
        name = n;
        desc = "";
        isSection = true;
    }

    public ContentItem(String n, String d) {
        name = n;
        desc = d;
    }
}
