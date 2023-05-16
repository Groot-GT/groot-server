package com.groot.mindmap.message.domain;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public abstract class Message {
    private final Long pageId;
    private final String type;

    public Long pageId() {
        return pageId;
    }
}
