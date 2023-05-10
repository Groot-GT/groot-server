package com.groot.mindmap.message.domain;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public abstract class Message {
    private final String pageId;
    private final String type;

    public String pageId() {
        return pageId;
    }
}
