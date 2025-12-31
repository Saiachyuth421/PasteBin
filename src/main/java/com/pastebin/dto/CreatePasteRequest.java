package com.pastebin.dto;

public class CreatePasteRequest {

    private String content;
    private Integer ttl_seconds;
    private Integer max_views;

    public String getContent() { return content; }
    public Integer getTtl_seconds() { return ttl_seconds; }
    public Integer getMax_views() { return max_views; }
}
