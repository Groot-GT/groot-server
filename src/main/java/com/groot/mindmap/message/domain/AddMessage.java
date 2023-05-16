package com.groot.mindmap.message.domain;

public class AddMessage extends Message{

    private final Long parentId;

    public AddMessage(Long pageId, Long parentId) {
        super(pageId, "ADD");
        this.parentId = parentId;
    }

    public Long parentId() {
        return parentId;
    }
}
