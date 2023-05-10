package com.groot.mindmap.message.domain;

public class DeleteMessage extends Message{

    private final Long nodeId;

    public DeleteMessage(String pageId, String type, Long nodeId) {
        super(pageId, type);
        this.nodeId = nodeId;
    }

    public Long nodeId() {
        return nodeId;
    }
}
