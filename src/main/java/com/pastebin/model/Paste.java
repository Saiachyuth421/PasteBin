package com.pastebin.model;

import jakarta.persistence.*;

@Entity
@Table(name = "pastes")
public class Paste {

    @Id
    private String id;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String content;

    private Long expiresAt;     // epoch millis
    private Integer maxViews;   // nullable
    private Integer views;
    private Long createdAt;

    public Paste() {}

    public Paste(String id, String content, Long expiresAt,
                 Integer maxViews, Integer views, Long createdAt) {
        this.id = id;
        this.content = content;
        this.expiresAt = expiresAt;
        this.maxViews = maxViews;
        this.views = views;
        this.createdAt = createdAt;
    }

    // ===== GETTERS =====
    public String getId() {
        return id;
    }

    public String getContent() {
        return content;
    }

    public Long getExpiresAt() {
        return expiresAt;
    }

    public Integer getMaxViews() {
        return maxViews;
    }

    public Integer getViews() {
        return views;
    }

    public Long getCreatedAt() {
        return createdAt;
    }

    // ===== SETTERS =====
    public void setId(String id) {
        this.id = id;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public void setExpiresAt(Long expiresAt) {
        this.expiresAt = expiresAt;
    }

    public void setMaxViews(Integer maxViews) {
        this.maxViews = maxViews;
    }

    public void setViews(Integer views) {
        this.views = views;
    }

    public void setCreatedAt(Long createdAt) {
        this.createdAt = createdAt;
    }
}
