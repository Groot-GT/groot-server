package com.groot.mindmap.message.domain;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class DeleteMessage extends Message{

    @JsonProperty("nodeId")
    private final Long nodeId;

    @JsonCreator(mode = JsonCreator.Mode.PROPERTIES)
    public DeleteMessage(@JsonProperty("pageId") Long pageId,
                         @JsonProperty("nodeId") Long nodeId) {
        super(pageId, "DELETE");
        this.nodeId = nodeId;
    }

    public Long nodeId() {
        return nodeId;
    }
}
