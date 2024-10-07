package com.hansung.customblog.model;

public enum NoticeType {
    IMPORTANT("중요"),
    GENERAL("일반");

    private final String description;

    NoticeType(String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        return description;
    }
}
