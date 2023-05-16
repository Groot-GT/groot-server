package com.groot.mindmap.message.domain;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class AddMessage extends Message{

    @JsonProperty("parentId")
    private final Long parentId;

    @JsonCreator(mode = JsonCreator.Mode.PROPERTIES)
    public AddMessage(@JsonProperty("pageId") Long pageId,
                      @JsonProperty("parentId") Long parentId) {
        super(pageId, "ADD");
        this.parentId = parentId;
    }

    public Long parentId() {
        return parentId;
    }
}
